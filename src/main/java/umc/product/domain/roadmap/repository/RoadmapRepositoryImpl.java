package umc.product.domain.roadmap.repository;

import static umc.product.domain.roadmap.entity.QRoadmap.roadmap;
import static umc.product.domain.roadmap.entity.QRoadmapSemester.roadmapSemester;
import static umc.product.domain.semester.entity.QSemester.semester;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.dto.response.admin.QRoadmapInfo;
import umc.product.domain.roadmap.dto.response.admin.RoadmapInfo;
import umc.product.domain.roadmap.entity.QRoadmapWeek;

@RequiredArgsConstructor
public class RoadmapRepositoryImpl implements RoadmapRepositoryCustom {
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<RoadmapInfo> searchRoadmaps(Long semesterId, Part part, String keyword, Pageable pageable) {
    // 서브쿼리: RoadmapWeek에서 roadmap_id별로 가장 큰 week 값을 찾음
    QRoadmapWeek roadmapWeekSub = new QRoadmapWeek("roadmapWeekSub");
    Expression<Integer> maxWeek = JPAExpressions
        .select(roadmapWeekSub.week.max())
        .from(roadmapWeekSub)
        .where(roadmapWeekSub.roadmap.id.eq(roadmap.id));

    // 메인 쿼리
    List<RoadmapInfo> content = jpaQueryFactory
        .select(new QRoadmapInfo( // DTO로 직접 프로젝션
            roadmap.id,
            roadmap.title,
            semester.name,
            roadmap.part,
            maxWeek // 서브쿼리 결과(총 주차)를 DTO 생성자에 포함
        ))
        .from(roadmap)
        .join(roadmap.roadmapSemesterList, roadmapSemester)
        .join(roadmapSemester.semester, semester)
        .where(
            semesterIdEq(semesterId), // 동적 조건 1: 기수
            partEq(part),             // 동적 조건 2: 파트
            keywordContains(keyword)  // 동적 조건 3: 검색어
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(roadmap.createdAt.desc())
        .fetch();

    // Count 쿼리
    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(roadmap.count())
        .from(roadmap)
        .join(roadmap.roadmapSemesterList, roadmapSemester)
        .join(roadmapSemester.semester, semester)
        .where(
            semesterIdEq(semesterId),
            partEq(part),
            keywordContains(keyword)
        );

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  // 동적 WHERE절을 위한 BooleanExpression 메서드들
  private BooleanExpression semesterIdEq(Long semesterId) {
    return semesterId != null ? semester.id.eq(semesterId) : null;
  }

  private BooleanExpression partEq(Part part) {
    return part != null ? roadmap.part.eq(part) : null;
  }

  private BooleanExpression keywordContains(String keyword) {
    return keyword != null && !keyword.isBlank() ? roadmap.title.containsIgnoreCase(keyword) : null;
  }

}
