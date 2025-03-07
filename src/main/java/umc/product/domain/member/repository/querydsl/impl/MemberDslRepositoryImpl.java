package umc.product.domain.member.repository.querydsl.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.member.entity.QMemberLoginInfo;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.repository.querydsl.MemberDslRepository;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.university.entity.University;
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

import static umc.product.domain.member.status.MemberErrorStatus.ERROR_TO_SAVE_DB;


@Repository
@Slf4j
@AllArgsConstructor
public class MemberDslRepositoryImpl implements MemberDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember qMember = QMember.member;
    private final QMemberLoginInfo qMemberLoginInfo = QMemberLoginInfo.memberLoginInfo;

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(qMember)
                        .where(qMember.id.eq(memberId))
                        .fetchFirst()
        );
    }

    @Override
    public Optional<Member> findMemberByClientId(String clientId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(qMember)
                        .join(qMember.memberLoginInfo, qMemberLoginInfo).fetchJoin()
                        .where(qMember.memberLoginInfo.memberLoginId.eq(clientId))
                        .fetchFirst()
        );
    }

    @Override
    public List<Member> findMemberList(Pageable pageable, Member currentMember, Long semesterId, Role role, Part part) {
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
        if (semesterId != null) {
            builder.and(qMember.memberSemesterPart.any().semester.id.eq(semesterId)
                        .or(qMember.memberSemesterPosition.any().semester.id.eq(semesterId))
        );
        }
        if (part != null) {
            builder.and(qMember.memberSemesterPart.any().part.eq(part));
        }
        builder.and(qMember.deletedAt.isNull());

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
        builder.and(qMember.name.contains(searchString).or(qMember.nickName.contains(searchString)));
        builder.and(qMember.deletedAt.isNull());

        if (member != null && member.getRole() != null) {
            if(member.getRole().equals(Role.SCHOOL_ADMIN)){
                builder.and(qMember.university.name.eq(member.getUniversity().getName()));
            }
            builder.and(qMember.role.gt(member.getRole()));
        }

        return jpaQueryFactory
                .selectFrom(qMember)
                .where(builder)
                .fetch();
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

    @Override
    public Optional<Member> findByClientIdAndLoginType(String clientId, LoginType loginType) {

        return Optional.ofNullable(jpaQueryFactory
                                    .selectFrom(qMember)
                                    .join(qMember.memberLoginInfo, qMemberLoginInfo).fetchJoin()
                                    .where(
                                            qMember.memberLoginInfo.memberLoginId.eq(clientId),
                                            qMember.loginType.eq(loginType)
                                    )
                                    .fetchOne());
    }

    @Override
    public boolean existsMemberByClientId(String clientId) {

        return jpaQueryFactory
                .selectOne()
                .from(qMember)
                .where(qMember.memberLoginInfo.memberLoginId.eq(clientId))
                .fetchFirst() != null;
    }
}
