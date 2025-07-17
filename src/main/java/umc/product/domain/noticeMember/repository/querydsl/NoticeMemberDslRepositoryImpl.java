package umc.product.domain.noticeMember.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import umc.product.domain.branch.entity.QBranch;
import umc.product.domain.branchUniversity.entity.BranchUniversity;
import umc.product.domain.branchUniversity.entity.QBranchUniversity;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.QNotice;
import umc.product.domain.notice.entity.enums.NoticeTarget;
import umc.product.domain.noticeMember.entity.QNoticeMember;
import umc.product.domain.university.entity.QUniversity;
import umc.product.domain.university.entity.University;

import org.springframework.data.domain.Pageable;
import java.util.List;

import static umc.product.domain.notice.entity.enums.NoticeTarget.*;

@Repository
@RequiredArgsConstructor
public class NoticeMemberDslRepositoryImpl implements NoticeMemberDslRepository {

    private final JPAQueryFactory queryFactory;

    // todo 뭔가 추상화 시켜서 하나로 통합해서, 읽기 상태 체크 상태조회할 수 있을 것 같음

    // 해당 공지의 타깃 멤버 목록 조회, checkedFilter가 null이면 열람 여부 필터링하지 않음 (모두 조회)
    public JPQLQuery<Member> findNoticeTargetMembers
    (Notice targetNotice) {

        QNotice notice = QNotice.notice;
        QNoticeMember noticeMember = QNoticeMember.noticeMember;
        QMember member = QMember.member;
        QUniversity university = QUniversity.university;
        QBranchUniversity branchUniversity = QBranchUniversity.branchUniversity;

        // 공지의 대상에 따라 필터링
        return queryFactory
                .select(member)
                .from(noticeMember)
                .join(noticeMember.member, member)
                .join(member.university, university)
                .where(
                        member.status.in(Status.ACTIVE, Status.OLD), // 활성화된 멤버만 조회
                        university.isActive.isTrue(), // 활성화된 대학에 속한 멤버만 조회
                        filterByNoticeTarget(targetNotice, university, branchUniversity) // 공지의 대상에 따라 필터링
                );
    }

    // 공지의 대상에 따라 필터링하는 메서드
    private BooleanExpression filterByNoticeTarget(Notice notice, QUniversity university, QBranchUniversity branchUniversity) {
        NoticeTarget target = notice.getTarget();
        University writerUniv = notice.getWriter().getUniversity();

        // 전체 공지
        if (target == CENTRAL) {
            return null;
        }

        if (target == UNIVERSITY) {
            // 대학 정보 찾기
            return university.id.eq(writerUniv.getId());
        }

        if (target == BRANCH) {
            // 지부 정보 찾기
            List<Long> branchUniversityIds = writerUniv.getBranchUniversityList().stream()
                    .filter(BranchUniversity::isActive)
                    .map(bu -> bu.getBranch().getId())
                    .toList();

            return branchUniversity.branch.id.in(branchUniversityIds)
                    .and(branchUniversity.university.id.eq(university.id))
                    .and(branchUniversity.isActive.isTrue());
        }

        return null;
    }

    // 멤버가 공지에 열람 여부 필터 처리
    @Override
    public Page<Member> findTargetMembersByReadStatus(Notice notice, Boolean isReadFilter, Pageable pageable) {
        QMember member = QMember.member;
        QNoticeMember noticeMember = QNoticeMember.noticeMember;

        JPQLQuery<Member> baseQuery = findNoticeTargetMembers(notice);

        // NoticeMember를 left join하여 isRead 여부 확인
        JPQLQuery<Member> query = baseQuery
                .leftJoin(noticeMember).on(
                        noticeMember.notice.eq(notice),
                        noticeMember.member.eq(member),
                        noticeMember.deletedAt.isNull()
                )
                .where(
                        filterByReadStatus(noticeMember, isReadFilter)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Member> content = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }



    // 공지 멤버의 열람 여부 필터링 메서드
    private BooleanExpression filterByReadStatus(QNoticeMember noticeMember, Boolean isReadFilter) {
        // isReadFilter가 null인 경우 필터링하지 않음
        if (isReadFilter == null) return null;

        if (isReadFilter) {
            // 읽은 사람만: NoticeMember가 존재하고 isRead == true
            return noticeMember.id.isNotNull().and(noticeMember.isRead.isTrue());
        } else {
            // 안 읽은 사람 + NoticeMember 없는 사람
            return noticeMember.id.isNull().or(noticeMember.isRead.isFalse());
        }

    }

    // 멤버가 공지에 체크 여부 필터 처리
    @Override
    public Page<Member> findTargetMembersByCheckStatus(Notice notice, Boolean isCheckedFilter, Pageable pageable) {
        QMember member = QMember.member;
        QNoticeMember noticeMember = QNoticeMember.noticeMember;

        JPQLQuery<Member> baseQuery = findNoticeTargetMembers(notice);

        // NoticeMember를 left join하여 isChecked 여부 확인
        JPQLQuery<Member> query = baseQuery
                .leftJoin(noticeMember).on(
                        noticeMember.notice.eq(notice),
                        noticeMember.member.eq(member),
                        noticeMember.deletedAt.isNull()
                )
                .where(
                        filterByCheckStatus(noticeMember, isCheckedFilter)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Member> content = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    // 공지 멤버의 체크 여부 필터링 메서드
    private BooleanExpression filterByCheckStatus(QNoticeMember noticeMember, Boolean isCheckedFilter) {
        // isCheckedFilter가 null인 경우 필터링하지 않음
        if (isCheckedFilter == null) return null;

        if (isCheckedFilter) {
            // 체크한 사람만
            return noticeMember.id.isNotNull().and(noticeMember.isChecked.isTrue());
        } else {
            // 체크 안 한 사람 + NoticeMember 없는 사람
            return noticeMember.id.isNull().or(noticeMember.isChecked.isFalse());
        }
    }

}

