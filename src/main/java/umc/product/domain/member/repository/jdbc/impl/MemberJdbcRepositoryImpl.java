package umc.product.domain.member.repository.jdbc.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.member.repository.jdbc.MemberJdbcRepository;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.global.common.exception.RestApiException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static umc.product.domain.member.status.MemberErrorStatus.DB_SAVE_ERROR;


@Repository
@Slf4j
@AllArgsConstructor
public class MemberJdbcRepositoryImpl implements MemberJdbcRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final JdbcTemplate jdbcTemplate;
    private final PlatformTransactionManager transactionManager;

    /**
     * 100명씩 Insert 작업을 위해 병렬처리(50개씩 나누어 한번에 저장)
     * JdbcTemplete로 구현
     * @return 저장된 Member의 리스트
     */
    @Transactional
    @Override
    public List<Member> saveRegisterNewMemberList(
            List<Member> memberList,
            List<SemesterPart> semesterPartList,
            List<SemesterPosition> semesterPositionList
    ) {
        final int batchSize = 50;  // 배치 크기 설정
        int threadPoolSize = Runtime.getRuntime().availableProcessors() * 2;
        LocalDateTime now = LocalDateTime.now();
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        String memberInsertSql = """
                    INSERT INTO member (avatar_url, client_id, created_at, deleted_at, email, login_type, name, nick_name, role, status, university_id, updated_at) 
                    VALUES
                    """;

        String semesterPositionSql = """
                    INSERT INTO semester_position (created_at, deleted_at, member_id, university_position, central_position, semester_id, updated_at) 
                    VALUES
                    """;

        String semesterPartSql = """
                    INSERT INTO semester_part (created_at, deleted_at, member_id, part, semester_id, updated_at)
                    VALUES
            """;

        //병렬처리의 return 값으로 List<Long> 반환
        List<CompletableFuture<List<Long>>> futures = new ArrayList<>();

        for (int start = 0; start < memberList.size(); start += batchSize) {
            int end = Math.min(start + batchSize, memberList.size());
            //batch size별로 리스트 분할
            List<Member> batchMembers = memberList.subList(start, end);
            List<SemesterPart> batchSemesterPart = semesterPartList.subList(start, end);
            List<SemesterPosition> batchSemesterPositions = semesterPositionList.subList(start, end );

            //병렬 수행
            CompletableFuture<List<Long>> future = CompletableFuture.supplyAsync(() -> {
                //중간에 잘못되면 Transaction을 통해 RollBack이 가능하게 설계
                TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
                return transactionTemplate.execute(status -> {
                    try {
                        StringBuilder memberValues = new StringBuilder();
                        List<Object> memberParams = new ArrayList<>();
                        for (Member member : batchMembers) {
                            processMember(member, memberValues, memberParams, now);
                        }
                        if (!memberValues.isEmpty()) memberValues.setLength(memberValues.length() - 1);

                        //생성된 Member의 Id를 찾기
                        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
                        jdbcTemplate.update(
                                connection -> {
                                    PreparedStatement ps = connection.prepareStatement(memberInsertSql + memberValues, Statement.RETURN_GENERATED_KEYS);
                                    for (int i = 0; i < memberParams.size(); i++) {
                                        ps.setObject(i + 1, memberParams.get(i));
                                    }
                                    return ps;
                                },
                                keyHolder
                        );

                        //생성된 Member의 Id를 찾기
                        List<Long> memberIdList = keyHolder.getKeyList().stream()
                                .map(key -> ((Number) key.get("GENERATED_KEY")).longValue())
                                .collect(Collectors.toList());

                        StringBuilder semesterPositionValues = new StringBuilder();
                        List<Object> semesterPositionParams = new ArrayList<>();
                        for (int i = 0; i < memberIdList.size(); i++) {
                            SemesterPosition semesterPosition = batchSemesterPositions.get(i);
                            processSemesterPosition(semesterPosition, memberIdList.get(i), semesterPositionValues, semesterPositionParams, now);
                        }

                        if (!semesterPositionValues.isEmpty()) semesterPositionValues.setLength(semesterPositionValues.length() - 1);
                        jdbcTemplate.update(semesterPositionSql + semesterPositionValues, semesterPositionParams.toArray());

                        StringBuilder semesterPartValues = new StringBuilder();
                        List<Object> semesterPartParams = new ArrayList<>();
                        for (int i = 0; i < memberIdList.size(); i++) {
                            SemesterPart semesterPart = batchSemesterPart.get(i);
                            processSemesterPart(semesterPart, memberIdList.get(i), semesterPartValues, semesterPartParams, now);
                        }

                        if (!semesterPartValues.isEmpty()) semesterPartValues.setLength(semesterPartValues.length() - 1);
                        if(!semesterPartParams.isEmpty()) jdbcTemplate.update(semesterPartSql + semesterPartValues, semesterPartParams.toArray());
                        return memberIdList;
                    } catch (Exception e) {
                        status.setRollbackOnly(); // 예외 발생 시 롤백 처리
                        throw new RestApiException(DB_SAVE_ERROR);
                    }
                });
            }, executor);
            futures.add(future);
        }

        List<Long> allMemberIds = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        executor.shutdown();

        return findMembersByIds(allMemberIds);
    }

    @Override
    public void saveRegisterExistMemberList(List<SemesterPart> semesterPartList, List<SemesterPosition> semesterPositionList) {
        String semesterPartSql = """
                    INSERT INTO semester_part (created_at, deleted_at, member_id, part, semester_id, updated_at)
                    VALUES
            """;

        String semesterPositionSql = """
                    INSERT INTO semester_position (created_at, deleted_at, member_id, university_position, central_position, semester_id, updated_at) 
                    VALUES
                    """;
        LocalDateTime now = LocalDateTime.now();
        //part insert
        StringBuilder semesterPartValues = new StringBuilder();
        List<Object> semesterPartParams = new ArrayList<>();
        for (int i = 0; i < semesterPartList.size(); i++) {
            SemesterPart semesterPart = semesterPartList.get(i);
            processSemesterPart(semesterPart, semesterPart.getMember().getId(), semesterPartValues, semesterPartParams, now);
        }

        if (!semesterPartValues.isEmpty()) semesterPartValues.setLength(semesterPartValues.length() - 1);
        if(!semesterPartParams.isEmpty()) jdbcTemplate.update(semesterPartSql + semesterPartValues, semesterPartParams.toArray());

        //position insert
        StringBuilder semesterPositionValues = new StringBuilder();
        List<Object> semesterPositionParams = new ArrayList<>();
        for (int i = 0; i < semesterPositionList.size(); i++) {
            SemesterPosition semesterPosition = semesterPositionList.get(i);
            processSemesterPosition(semesterPosition, semesterPosition.getMember().getId() ,semesterPositionValues, semesterPositionParams, now);
        }

        if (!semesterPositionValues.isEmpty()) semesterPositionValues.setLength(semesterPositionValues.length() - 1);
        jdbcTemplate.update(semesterPositionSql + semesterPositionValues, semesterPositionParams.toArray());
    }

    private List<Member> findMembersByIds(
            List<Long> memberIds
    ) {
        if (memberIds.isEmpty()) {
            return Collections.emptyList();
        }
        return jpaQueryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.id.in(memberIds))
                .fetch();
    }

    private void processMember(
            Member member,
            StringBuilder memberValues,
            List<Object> memberParams,
            LocalDateTime now
    ) {
        memberValues.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?),");
        memberParams.addAll(Arrays.asList(
                null, null, Timestamp.valueOf(now), null, null, null,
                member.getName(), member.getNickName(), member.getRole().getPriority(),
                Status.WAITING_FOR_UPDATE.name(), member.getUniversity().getId(), Timestamp.valueOf(now)
        ));
    }

    private void processSemesterPosition(
            SemesterPosition semesterPosition,
            Long memberId,
            StringBuilder semesterPositionValues,
            List<Object> semesterPositionParams,
            LocalDateTime now
    ) {
        if (semesterPosition.getCentralPosition() != null || semesterPosition.getUniversityPosition() != null) {
            semesterPositionValues.append("(?, ?, ?, ?, ?, ?, ?),");
            semesterPositionParams.addAll(Arrays.asList(
                    Timestamp.valueOf(now),
                    null,
                    memberId,
                    semesterPosition.getUniversityPosition(),
                    semesterPosition.getCentralPosition(),
                    semesterPosition.getSemester().getId(),
                    Timestamp.valueOf(now)
            ));
        }
    }

    private void processSemesterPart(
            SemesterPart semesterPart,
            Long memberId,
            StringBuilder semesterPartValues,
            List<Object> semesterPartParams,
            LocalDateTime now
    ) {
        if (semesterPart.getPart() != null) {
            semesterPartValues.append("(?, ?, ?, ?, ?, ?),");
            semesterPartParams.addAll(Arrays.asList(
                    Timestamp.valueOf(now), null, memberId, semesterPart.getPart().name(),
                    semesterPart.getSemester().getId(), Timestamp.valueOf(now)
            ));
        }
    }
}
