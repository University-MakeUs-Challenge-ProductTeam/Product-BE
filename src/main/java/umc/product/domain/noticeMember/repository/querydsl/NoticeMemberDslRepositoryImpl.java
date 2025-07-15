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

    // 해당 공지의 타깃 멤버 목록 조회, checkedFilter가 null이면 열람 여부 필터링하지 않음 (모두 조회)
    @Override
    public Page<Member> findNoticeTargetMembers
    (Notice targetNotice, Boolean checkedFilter, Pageable pageable) {

        QNotice notice = QNotice.notice;
        QNoticeMember noticeMember = QNoticeMember.noticeMember;
        QMember member = QMember.member;
        QUniversity university = QUniversity.university;
        QBranchUniversity branchUniversity = QBranchUniversity.branchUniversity;
        QBranch branch = QBranch.branch;

        // todo 해당 기수, 해당 파트 별 필터링

        // 공지의 대상에 따라 필터링
        JPQLQuery<Member> query = queryFactory
                .select(member)
                .from(noticeMember)
                .join(noticeMember.member, member)
                .join(member.university, university)
                .where(
                        member.status.in(Status.ACTIVE, Status.OLD), // 활성화된 멤버만 조회
                        university.isActive.isTrue(), // 활성화된 대학에 속한 멤버만 조회
                        filterByNoticeTarget(targetNotice, university, branchUniversity), // 공지의 대상에 따라 필터링
                        filterByChecked(noticeMember, checkedFilter) // 열람 여부에 따라 필터링
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 쿼리 실행 및 결과 페이징 처리
        List<Member> content = query.fetch();
        long total = query.fetchCount();

        // Page 객체 생성 및 반환
        return new PageImpl<>(content, pageable, total);
    }

    // 열람 여부에 따라 필터링하는 메서드
    private BooleanExpression filterByChecked(QNoticeMember nm, Boolean checked) {
        if (checked == null) return null;
        return checked ? nm.isChecked.isTrue() : nm.isChecked.isFalse();
    }

    // 공지의 대상에 따라 필터링하는 메서드
    private BooleanExpression filterByNoticeTarget(Notice notice, QUniversity university, QBranchUniversity branchUniversity) {
        NoticeTarget target = notice.getTarget();
        University writerUniv = notice.getWriter().getUniversity();

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
}

