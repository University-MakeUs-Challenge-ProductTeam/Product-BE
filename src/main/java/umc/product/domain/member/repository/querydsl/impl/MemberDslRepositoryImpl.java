package umc.product.domain.member.repository.querydsl.impl;

import static umc.product.domain.member.entity.QMember.member;
import static umc.product.domain.semester.entity.QSemester.semester;
import static umc.product.domain.semester.entity.QSemesterPart.semesterPart;
import static umc.product.domain.university.entity.QUniversity.university;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.member.entity.QMemberLoginInfo;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.repository.querydsl.MemberDslRepository;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.semester.entity.QSemester;
import umc.product.domain.semester.entity.QSemesterPart;
import umc.product.domain.study.dto.response.admin.MemberSearchInfo;
import umc.product.domain.study.dto.response.admin.QMemberSearchInfo;
import umc.product.domain.university.entity.QUniversity;
import umc.product.domain.university.entity.University;

import java.util.*;
import java.util.stream.Collectors;


@Repository
@Slf4j
@AllArgsConstructor
public class MemberDslRepositoryImpl implements MemberDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember qMember = member;
    private final QMemberLoginInfo qMemberLoginInfo = QMemberLoginInfo.memberLoginInfo;
    private final QUniversity qUniversity = university;

    @Override
    public boolean existById(Long memberId) {
        return jpaQueryFactory
                .selectFrom(qMember)
                .where(qMember.id.eq(memberId))
                .fetchFirst() != null;
    }

    @Override
    public List<Member> findByNameAndNickNameAndUniversity(List<AdminRegisterListRequest.AdminRegisterMemberRequest> registerMemberList) {
        List<String> names = registerMemberList.stream()
                .map(AdminRegisterListRequest.AdminRegisterMemberRequest::name)
                .collect(Collectors.toList());

        List<String> nickNames = registerMemberList.stream()
                .map(AdminRegisterListRequest.AdminRegisterMemberRequest::nickName)
                .collect(Collectors.toList());

        List<String> universityNames = registerMemberList.stream()
                .map(AdminRegisterListRequest.AdminRegisterMemberRequest::universityName)
                .collect(Collectors.toList());

        return jpaQueryFactory
                .selectFrom(qMember)
                .join(qMember.university, qUniversity).fetchJoin()
                .where(qMember.name.in(names)
                        .and(qMember.nickName.in(nickNames))
                        .and(qUniversity.name.in(universityNames)))
                .fetch();
    }

    @Override
    public void updateAvatarImage(Member member, String avatarUrl) {
        jpaQueryFactory
                .update(qMember)
                .set(qMember.avatarUrl, avatarUrl)
                .where(qMember.id.eq(member.getId()))
                .execute();
    }

    @Override
    public Optional<Member> findByIdAndFetchLoginInfo(Long memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(qMember)
                        .join(qMember.memberLoginInfo, qMemberLoginInfo).fetchJoin()
                        .where(qMember.id.eq(memberId))
                        .fetchFirst()
        );
    }

    @Override
    public Optional<Member> findByIdNotFetchLoginInfo(Long memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(qMember)
                        .where(qMember.id.eq(memberId))
                        .fetchFirst()
        );
    }

    /**
     * 소셜로그인용
     */
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
        builder.and(qMember.deletedAt.isNull());    //삭제된 사용자는 검색에서 제외.
        builder.and(qMember.loginType.isNull()
                        .or(qMember.loginType.ne(LoginType.INTERNAL))); //웹 계정은 제외
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
        builder.and(qMember.loginType.isNull()
                        .or(qMember.loginType.ne(LoginType.INTERNAL))); //웹 계정은 제외
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

    @Override
    public long countMemberByFilter(
        Member currentMember, 
        Long semesterId, 
        Role role, 
        Part part
        ) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMember.deletedAt.isNull());
        builder.and(qMember.loginType.isNull()
                .or(qMember.loginType.ne(LoginType.INTERNAL)));
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
                .select(qMember.count())
                .from(qMember)
                .where(builder)
                .fetchOne();
    }

    @Override
    public Page<MemberSearchInfo> searchMembers(Long universityId, String keyword, Pageable pageable) {

        QSemester semesterSub = new QSemester("semesterSub");
        QSemesterPart semesterPartSub = new QSemesterPart("semesterPartSub");
        Expression<Integer> maxGeneration = JPAExpressions
            .select(semesterSub.name.castToNum(Integer.class).max())
            .from(semesterPartSub)
            .join(semesterPartSub.semester, semesterSub)
            .where(semesterPartSub.member.id.eq(member.id));

        // 메인 쿼리: 데이터 목록을 조회합니다.
        List<MemberSearchInfo> content = jpaQueryFactory
            .select(new QMemberSearchInfo(
                member.id,
                member.nickName,
                member.name,
                university.name,
                semesterPart.part,
                semester.name
            ))
            .from(member)
            .join(member.university, university)
            .join(member.memberSemesterPart, semesterPart)
            .join(semesterPart.semester, semester)
            .where(
                universityIdEq(universityId),
                keywordContains(keyword),
                semester.name.castToNum(Integer.class).eq(maxGeneration)
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(semester.name.castToNum(Integer.class).desc(), member.createdAt.desc())
            .fetch();

        // Count 쿼리: 전체 개수를 조회합니다.
        JPAQuery<Long> countQuery = jpaQueryFactory
            .select(member.count())
            .from(member)
            .join(member.university, university)
            .where(
                universityIdEq(universityId),
                keywordContains(keyword)
            );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    // 학교 ID로 필터링하는 동적 조건
    private BooleanExpression universityIdEq(Long universityId) {
        return universityId != null ? university.id.eq(universityId) : null;
    }

    // 키워드로 필터링하는 동적 조건 (닉네임 또는 이름)
    private BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return member.nickName.containsIgnoreCase(keyword).or(member.name.containsIgnoreCase(keyword));
    }
}
