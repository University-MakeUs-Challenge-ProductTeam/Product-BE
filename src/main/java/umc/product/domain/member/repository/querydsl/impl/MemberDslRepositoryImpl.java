package umc.product.domain.member.repository.querydsl.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.member.entity.QMemberLoginInfo;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.repository.querydsl.MemberDslRepository;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.university.entity.University;

import java.util.*;


@Repository
@Slf4j
@AllArgsConstructor
public class MemberDslRepositoryImpl implements MemberDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember qMember = QMember.member;
    private final QMemberLoginInfo qMemberLoginInfo = QMemberLoginInfo.memberLoginInfo;

    @Override
    public void updateAvatarImage(Member member, String avatarUrl) {
        jpaQueryFactory
                .update(qMember)
                .set(qMember.avatarUrl, avatarUrl)
                .where(qMember.id.eq(member.getId()))
                .execute();
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(qMember)
                        .join(qMember.memberLoginInfo, qMemberLoginInfo).fetchJoin()
                        .where(qMember.id.eq(memberId))
                        .fetchFirst()
        );
    }

    @Override
    public Optional<Member> findByIdForSignup(Long memberId) {
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
                        //fetchJoin하여 한번에 가져옴
                        .join(qMember.memberLoginInfo, qMemberLoginInfo).fetchJoin()
                        .where(qMember.memberLoginInfo.memberLoginId.eq(clientId))
                        .fetchFirst()
        );
    }

    @Override
    public Page<Member> findMemberListByFilter(
            Pageable pageable,
            Member currentMember,
            Long semesterId,
            Role role,
            Part part
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        if (currentMember != null && currentMember.getRole() != null) {
            //학교 web 계정이라면 query에 university를 추가
            if(currentMember.getRole().equals(Role.SCHOOL_ADMIN)){
                builder.and(qMember.university.name.eq(currentMember.getUniversity().getName()));
            }
            //검색되는 member의 범위는 role의 priority가 더 낮은 것들만
            builder.and(qMember.role.gt(currentMember.getRole()));
        }

        if (role != null) {
            builder.and(qMember.role.eq(role));
        }
        if (semesterId != null) {
            //직책 및 파트에 포함된 semester 모두를 검색
            builder.and(qMember.memberSemesterPart.any().semester.id.eq(semesterId)
                        .or(qMember.memberSemesterPosition.any().semester.id.eq(semesterId))
        );
        }
        if (part != null) {
            builder.and(qMember.memberSemesterPart.any().part.eq(part));
        }
        builder.and(qMember.deletedAt.isNull());

        long total = jpaQueryFactory
                .select(qMember.count())
                .from(qMember)
                .where(builder)
                .fetchOne();

        List<Member> memberList =  jpaQueryFactory
                .selectFrom(qMember)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(memberList, pageable, total);
    }

    @Override
    public Page<Member> findMembersBySearchString(
            Member member,
            Pageable pageable,
            String searchString
    ) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMember.name.contains(searchString).or(qMember.nickName.contains(searchString)));
        builder.and(qMember.deletedAt.isNull());    //삭제된 사용자는 검색에서 제외.

        if (member != null && member.getRole() != null) {
            //학교 web 계정이라면 query에 university 추가
            if(member.getRole().equals(Role.SCHOOL_ADMIN)){
                builder.and(qMember.university.name.eq(member.getUniversity().getName()));
            }
            builder.and(qMember.role.gt(member.getRole()));
        }

        long total = jpaQueryFactory
                .select(qMember.count())
                .from(qMember)
                .where(builder)
                .fetchOne();

        List<Member> memberList =  jpaQueryFactory
                .selectFrom(qMember)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(memberList, pageable, total);
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
