package umc.product.domain.study.repository.admin;

import static umc.product.domain.semester.entity.QSemester.semester;
import static umc.product.domain.semester.entity.QSemesterPart.semesterPart;
import static umc.product.domain.study.entity.QStudy.study;
import static umc.product.domain.study.entity.QStudyMember.studyMember;
import static umc.product.domain.study.entity.QStudyUniversity.studyUniversity;
import static umc.product.domain.university.entity.QUniversity.university;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.study.dto.response.admin.QStudyInfo;
import umc.product.domain.study.dto.response.admin.StudyInfo;

@RequiredArgsConstructor
public class AdminStudyRepositoryImpl implements AdminStudyRepositoryCustom {
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<StudyInfo> searchStudies(Long semesterId, Part part, String keyword, Long universityId, Pageable pageable) {
    List<StudyInfo> content = jpaQueryFactory
        .select(new QStudyInfo(
            study.id,
            university.name,
            study.name,
            semester.name,
            semesterPart.part,
            studyMember.id.countDistinct(),
            study.studyType,
            study.currentWeek
        ))
        .from(study)
        .join(study.studyUniversityList, studyUniversity)
        .join(studyUniversity.university, university)
        .join(study.studyMemberList, studyMember)
        .join(studyMember.semesterPart, semesterPart)
        .join(semesterPart.semester, semester)
        .where(
            semesterIdEq(semesterId),
            partEq(part),
            keywordContains(keyword),
            universityIdEq(universityId)
        )
        .groupBy(study.id, university.name, semester.name, semesterPart.part, study.studyType, study.currentWeek)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(study.createdAt.desc()) // 정렬 조건 추가
        .fetch();

    JPAQuery<Long> countQuery = jpaQueryFactory
        .select(study.id.countDistinct())
        .from(study)
        .join(study.studyUniversityList, studyUniversity)
        .join(studyUniversity.university, university)
        .join(study.studyMemberList, studyMember)
        .join(studyMember.semesterPart, semesterPart)
        .join(semesterPart.semester, semester)
        .where(
            semesterIdEq(semesterId),
            partEq(part),
            keywordContains(keyword),
            universityIdEq(universityId)
        );

    return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
  }

  private BooleanExpression semesterIdEq(Long semesterId) {
    return semesterId != null ? semester.id.eq(semesterId) : null;
  }

  private BooleanExpression partEq(Part part) {
    return part != null ? semesterPart.part.eq(part) : null;
  }

  private BooleanExpression keywordContains(String keyword) {
    return keyword != null && !keyword.isBlank() ? study.name.containsIgnoreCase(keyword) : null;
  }

  // 학교 관리자를 위한 동적 조건
  private BooleanExpression universityIdEq(Long universityId) {
    return universityId != null ? university.id.eq(universityId) : null;
  }

}
