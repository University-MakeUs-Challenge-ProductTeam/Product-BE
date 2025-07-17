package umc.product.domain.notice.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.product.domain.event.entity.event.QEvent;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.QNotice;
import umc.product.domain.notice.entity.QNoticePart;
import umc.product.domain.notice.entity.QNoticeSemester;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeDslRepositoryImpl implements NoticeDslRepository {

    private final JPAQueryFactory queryFactory;

    // 운영진용 공지 목록 조회
    @Override
    public Page<Notice> searchAdminNotices(AdminNoticeListRequest req, Pageable pageable) {
        QNotice notice = QNotice.notice;
        QEvent event = QEvent.event;
        QNoticeSemester ns = QNoticeSemester.noticeSemester;
        QNoticePart np = QNoticePart.noticePart;

        // 키워드 검색 조건을 포함한 JPQLQuery 생성
        JPQLQuery<Notice> query = queryFactory
                .selectDistinct(notice)
                .from(notice)
                .leftJoin(notice.event, event).fetchJoin()
                .leftJoin(notice.noticeSemesters, ns)
                .leftJoin(notice.noticeParts, np)
                .where(
                        keywordContains(req.keyword(), notice),
                        req.target() != null ? notice.target.eq(req.target()) : null,
                        req.semesterIds() != null ? ns.semester.id.in(req.semesterIds()) : null,
                        req.parts() != null ? np.part.in(req.parts()) : null,
                        req.eventMonth() != null ? event.eventStartDate.month().eq(req.eventMonth()) : null
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(notice.createdAt.desc());

        // 쿼리 실행 및 결과 페이징 처리
        List<Notice> content = query.fetch();
        long total = query.fetchCount();

        // Page 객체 생성 및 반환
        return new PageImpl<>(content, pageable, total);
    }

    // 키워드 검색 조건 (제목, 본문, 해시태그)
    private BooleanExpression keywordContains(String keyword, QNotice notice) {
        if (keyword == null || keyword.isBlank()) return null;

        return notice.title.containsIgnoreCase(keyword)
                .or(notice.content.containsIgnoreCase(keyword))
                .or(notice.hashtags.containsIgnoreCase(keyword));
    }

}

