package umc.product.domain.member.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.university.entity.QUniversity;
import umc.product.domain.university.entity.University;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Repository
@Slf4j
@AllArgsConstructor
public class MemberCustomRepositoryImpl implements umc.product.domain.member.repository.MemberCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final JdbcTemplate jdbcTemplate;
    private final QMember qMember = QMember.member;
    private final QUniversity qUniversity = QUniversity.university;

    @Override
    public List<Member> findMembers(Pageable pageable, Member currentMember, String semester, Role role, Part part) {
        BooleanBuilder builder = new BooleanBuilder();

        if (currentMember != null && currentMember.getRole() != null) {
            if(currentMember.getRole().equals(Role.SCHOOL_ADMIN)){
                builder.and(qMember.university.name.eq(currentMember.getUniversity().getName()));
            }
            builder.and(qMember.role.gt(currentMember.getRole()));
        }

        if (role != null) {
            builder.and(qMember.role.eq(role));
        }
        if (semester != null) {
            builder.and(qMember.memberSemesterPart.any().semester.name.eq(semester)
                        .or(qMember.memberSemesterPosition.any().semester.name.eq(semester))
        );
        }
        if (part != null) {
            builder.and(qMember.memberSemesterPart.any().part.eq(part));
        }

        return jpaQueryFactory
                .selectFrom(qMember)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Member> findMembersBySearchString(Member member, String searchString) {
        BooleanBuilder builder = new BooleanBuilder();

        if (member != null && member.getRole() != null) {
            if(member.getRole().equals(Role.SCHOOL_ADMIN)){
                builder.and(qMember.university.name.eq(member.getUniversity().getName()));
            }
            builder.and(qMember.role.gt(member.getRole()));
        }

        builder.or(qMember.name.eq(searchString));
        builder.or(qMember.nickName.eq(searchString));

        return jpaQueryFactory
                .selectFrom(qMember)
                .where(builder)
                .fetch();
    }

    @Override
    public void saveRegisterMembers(List<Member> memberList, List<SemesterPosition> semesterPositionList) {
        final int batchSize = 50;  // 배치 크기 설정
        int threadPoolSize = Runtime.getRuntime().availableProcessors() * 2;
        LocalDateTime now = LocalDateTime.now();
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        String memberSql = """ 
                           INSERT INTO member (avatar_url, client_id, created_at, deleted_at, email, login_type, name, nick_name, role, status, university_id, updated_at) 
                           VALUES
                           """;

        String semesterSql = """
                             INSERT INTO semester_position (created_at, deleted_at, member_id, position, semester_id, updated_at) 
                             VALUES
                             """;

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int start = 0; start < memberList.size(); start += batchSize) {
            int end = Math.min(start + batchSize, memberList.size());
            List<Member> batchMembers = memberList.subList(start, end);
            List<SemesterPosition> batchSemesterPositions = semesterPositionList.subList(start*2, end*2);

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    StringBuilder memberValues = new StringBuilder();
                    List<Object> memberParams = new ArrayList<>();

                    StringBuilder semesterValues = new StringBuilder();
                    List<Object> semesterParams = new ArrayList<>();

                    for (Member member : batchMembers) {
                        memberValues.append("(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?),");
                        memberParams.addAll(Arrays.asList(
                                null, null, Timestamp.valueOf(now), null, null, null,
                                member.getName(), member.getNickName(), member.getRole().getPriority(),
                                Status.WAITING_FOR_UPDATE.name(), member.getUniversity().getId(), Timestamp.valueOf(now)
                        ));
                    }

                    if (!memberValues.isEmpty()) memberValues.setLength(memberValues.length() - 1);

                    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

                    jdbcTemplate.update(
                            connection -> {
                                PreparedStatement ps = connection.prepareStatement(memberSql + memberValues.toString(), Statement.RETURN_GENERATED_KEYS);
                                for (int i = 0; i < memberParams.size(); i++) {
                                    ps.setObject(i + 1, memberParams.get(i));
                                }
                                return ps;
                            },
                            keyHolder
                    );

                    List<Long> memberIdList = keyHolder.getKeyList().stream()
                            .map(key -> ((Number) key.get("GENERATED_KEY")).longValue())
                            .toList();


                    for (int i = 0; i < memberIdList.size(); i += 1) {
                        SemesterPosition semesterPosition1 = batchSemesterPositions.get(i * 2);

                        if (semesterPosition1.getPosition() != null) {
                            semesterValues.append("(?, ?, ?, ?, ?, ?),");
                            semesterParams.addAll(Arrays.asList(
                                    Timestamp.valueOf(now), null, memberIdList.get(i), semesterPosition1.getPosition(),
                                    semesterPosition1.getSemester().getId(), Timestamp.valueOf(now)
                            ));
                        }

                        if (i + 1 < batchSemesterPositions.size()) {
                            SemesterPosition semesterPosition2 = batchSemesterPositions.get(i * 2 + 1);

                            if (semesterPosition2.getPosition() != null) {
                                semesterValues.append("(?, ?, ?, ?, ?, ?),");
                                semesterParams.addAll(Arrays.asList(
                                        Timestamp.valueOf(now), null, memberIdList.get(i), semesterPosition2.getPosition(),
                                        semesterPosition2.getSemester().getId(), Timestamp.valueOf(now)
                                ));
                            }
                        }
                    }

                    if (!semesterValues.isEmpty()) semesterValues.setLength(semesterValues.length() - 1);

                    jdbcTemplate.update(
                            semesterSql + semesterValues,
                            semesterParams.toArray()
                    );

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, executor);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
    }




    @Override
    public List<Member> findWaitingMemberByUniversity(University university) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMember.status.eq(Status.WAITING_FOR_UPDATE));
        builder.and(qMember.university.eq(university));

        return jpaQueryFactory
                .selectFrom(qMember)
                .where(builder)
                .fetch();
    }

    @Override
    public List<Member> findWaitingMember() {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMember.status.eq(Status.WAITING_FOR_UPDATE));
        builder.and(qMember.role.in(Role.ADMIN, Role.CENTRAL_ADMIN, Role.SCHOOL_ADMIN));


        return jpaQueryFactory
                .selectFrom(qMember)
                .where(builder)
                .fetch();
    }


    private Map<String, Member> findExistingMembers(List<Member> memberList) {
        List<String> names = memberList.stream().map(Member::getName).toList();
        List<String> nickNames = memberList.stream().map(Member::getNickName).toList();

        List<Member> existingMembers = jpaQueryFactory
                .selectFrom(qMember)
                .where(qMember.name.in(names).or(qMember.nickName.in(nickNames)))
                .fetch();

        return existingMembers.stream()
                .collect(Collectors.toMap(m -> m.getName() + "|" + m.getNickName(), m -> m));  //  "이름|닉네임" 기준으로 Map 변환
    }

    private Map<String, University> findUniversityList() {
        List<University> universityList = jpaQueryFactory
                .selectFrom(qUniversity)
                .fetch();

        return universityList.stream()
                .collect(Collectors.toMap(University::getName, university -> university));
    }
}
