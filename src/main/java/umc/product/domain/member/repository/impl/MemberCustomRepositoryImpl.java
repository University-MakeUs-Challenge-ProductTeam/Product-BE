package umc.product.domain.member.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class MemberCustomRepositoryImpl implements umc.product.domain.member.repository.MemberCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMember qMember = QMember.member;

    @Override
    public List<Member> findMembers(Pageable pageable, Member currentMember, String semester, Role role, String part) {
        BooleanBuilder builder = new BooleanBuilder();

        if (currentMember != null && currentMember.getRole() != null) {
            if(currentMember.getRole().equals(Role.UNIVERSITY_ADMIN)|| currentMember.getRole().equals(Role.BRANCH_ADMIN)){
                builder.and(qMember.university.name.eq(currentMember.getUniversity().getName()));
            }
            builder.and(qMember.role.gt(currentMember.getRole()));
        }

        if (role != null) {
            builder.and(qMember.role.eq(role));
        }
        if (semester != null) {
            builder.and(qMember.name.eq(semester));
        }
        if (part != null) {
            builder.and(qMember.name.eq(part));
        }

        return jpaQueryFactory
                .selectFrom(qMember)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Member> findMembersBySearchString(String searchString) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qMember.name.eq(searchString));
        builder.and(qMember.nickName.eq(searchString));

        return jpaQueryFactory
                .selectFrom(qMember)
                .where(builder)
                .fetch();
    }
}
