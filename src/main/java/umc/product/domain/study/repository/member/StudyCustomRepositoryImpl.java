package umc.product.domain.study.repository.member;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.checklist.entity.QChecklist;
import umc.product.domain.checklist.entity.QChecklistContent;
import umc.product.domain.checklist.entity.QChecklistMemberAnswer;
import umc.product.domain.checklist.entity.enums.ChecklistType;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.roadmap.entity.QRoadmap;
import umc.product.domain.roadmap.entity.QRoadmapSemester;
import umc.product.domain.semester.entity.QSemesterPart;
import umc.product.domain.study.dto.response.member.*;
import umc.product.domain.study.entity.QStudyAttendance;
import umc.product.domain.study.entity.QStudyMember;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StudyCustomRepositoryImpl implements StudyCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QStudyMember studyMember = QStudyMember.studyMember;
    private final QMember member = QMember.member;
    private final QStudyAttendance studyAttendance = QStudyAttendance.studyAttendance;
    private final QSemesterPart semesterPart = QSemesterPart.semesterPart;
    private final QChecklist checklist = QChecklist.checklist;
    private final QChecklistContent checklistContent = QChecklistContent.checklistContent;
    private final QChecklistMemberAnswer checklistMemberAnswer = QChecklistMemberAnswer.checklistMemberAnswer;
    private final QRoadmapSemester roadmapSemester = QRoadmapSemester.roadmapSemester;
    private final QRoadmap roadmap = QRoadmap.roadmap;

    @Override
    public List<StudyMemberResponse> getStudyMembers(Study study) {

        return jpaQueryFactory
                .select(new QStudyMemberResponse(
                        member.id,
                        member.university.name,
                        member.nickName,
                        studyAttendance.checkStatus.stringValue()
                ))
                .from(studyMember)
                .join(studyMember.semesterPart, semesterPart)
                .join(semesterPart.member, member)
                .leftJoin(studyAttendance)
                .on(studyAttendance.studyMember.eq(studyMember)
                        .and(studyAttendance.week.eq(study.getCurrentWeek())))
                .where(studyMember.study.id.eq(study.getId()))
                .fetch();
    }

    @Override
    public List<StudyWorkbookResponse.StudyChecklistResponse> getStudyChecklists(Long studyMemberId, int week) {

        // 체크리스트 입력 전 -> checkStatus가 전부 다 false -> 빈 리스트 반환 //
        Long totalTrueCount = jpaQueryFactory
                .select(checklistMemberAnswer.id.count())
                .from(checklist)
                .join(checklist.roadmapSemester, roadmapSemester)
                .join(roadmapSemester.roadmap, roadmap)
                .where(roadmap.week.eq(week))
                .join(checklist.checklistContentList, checklistContent)
                .leftJoin(checklistContent.checklistMemberAnswerList, checklistMemberAnswer)
                .on(checklistMemberAnswer.studyMember.id.eq(studyMemberId)
                        .and(checklistMemberAnswer.checkStatus.eq(true)))
                .fetchOne();
        if (totalTrueCount == null || totalTrueCount == 0) {
            return Collections.emptyList();
        }

        // 체크리스트 입력 후 입력한 체크리스트 값 반환 - SELECT, MULTIPLE 로 분기를 나눔 //
        // SELECT 타입 - "네, 참석했어요" 또는 "네, 모두 채웠어요"인 경우만 1로 처리 (이 contnet 값은 정해져 있기 때문에, 매번 해당 content_id를 받아오는 것보다 낫다고 판단)
        SimpleExpression<Integer> posSelectCase = new CaseBuilder()
                .when(checklistContent.content.in("네, 참석했어요", "네, 모두 채웠어요")
                        .and(checklistMemberAnswer.checkStatus.eq(true)))
                .then(Expressions.numberTemplate(Integer.class, "1"))
                .otherwise(Expressions.numberTemplate(Integer.class, "0"));
        NumberExpression<Integer> posSelectSum = Expressions.numberTemplate(Integer.class, "SUM({0})", posSelectCase);

        // SELECT 타입 - "아니요, 참석하지 못 했어요" 또는 "아니요, 다 채우지 못 했어요"
        SimpleExpression<Integer> negSelectCase = new CaseBuilder()
                .when(checklistContent.content.in("아니요, 참석하지 못 했어요", "아니요, 다 채우지 못 했어요")
                        .and(checklistMemberAnswer.checkStatus.eq(true)))
                .then(Expressions.numberTemplate(Integer.class, "1"))
                .otherwise(Expressions.numberTemplate(Integer.class, "0"));
        NumberExpression<Integer> negSelectSum = Expressions.numberTemplate(Integer.class, "SUM({0})", negSelectCase);

        // SELECT 타입 총 응답
        NumberExpression<Integer> selectTotal = Expressions.numberTemplate(
                Integer.class, "({0} + {1})", posSelectSum, negSelectSum);

        // SELECT 타입 - 긍정 응답이 있으면 "YES", 그렇지 않으면 "NO"
        StringExpression selectStatus = new CaseBuilder()
                .when(posSelectSum.gt(Expressions.numberTemplate(Integer.class, "0")))
                .then("YES")
                .when(negSelectSum.gt(Expressions.numberTemplate(Integer.class, "0")))
                .then("NO")
                .otherwise("NO");

        // MULTIPLE 타입 - 단순히 checklistMemberAnswer.checkStatus가 true인 경우를 1, 아니면 0으로 처리하여 집계
        SimpleExpression<Integer> multipleCase = new CaseBuilder()
                .when(checklistMemberAnswer.checkStatus.eq(true))
                .then(Expressions.numberTemplate(Integer.class, "1"))
                .otherwise(Expressions.numberTemplate(Integer.class, "0"));
        NumberExpression<Integer> multipleCount = Expressions.numberTemplate(Integer.class, "SUM({0})", multipleCase);

        // MULTIPLE 타입 - 해당 체크리스트의 전체 옵션 개수
        NumberExpression<Integer> totalOptions = checklistContent.id.countDistinct().castToNum(Integer.class);

        // MULTIPLE 타입 상태
        // -> multipleCount가 0이면 "NO"
        // -> multipleCount가 전체 옵션 개수인 totalOptions와 같으면 "YES"
        // -> 그 외는 "PARTIAL"
        StringExpression multipleStatus = new CaseBuilder()
                .when(multipleCount.eq(Expressions.numberTemplate(Integer.class, "0")))
                .then("NO")
                .when(multipleCount.eq(totalOptions))
                .then("YES")
                .otherwise("PARTIAL");

        // 최종 상태 결정 - checklist_type에 따라 SELECT 타입은 selectStatus, MULTIPLE 타입은 multipleStatus 사용
        StringExpression statusExpression = new CaseBuilder()
                .when(checklist.checklistType.eq(ChecklistType.SELECT))
                .then(selectStatus)
                .when(checklist.checklistType.eq(ChecklistType.MULTIPLE))
                .then(multipleStatus)
                .otherwise("NO");

        // 최종 조회 - checklist_category를 stringValue()로 전달하고, 계산된 status를 함께 반환
        return jpaQueryFactory
                .select(new QStudyWorkbookResponse_StudyChecklistResponse(
                        checklist.checklistCategory.stringValue(),
                        statusExpression.stringValue()
                ))
                .from(checklist)
                // 해당 체크리스트가 속한 Roadmap의 주차 조건 적용
                .join(checklist.roadmapSemester, roadmapSemester)
                .join(roadmapSemester.roadmap, roadmap)
                .where(roadmap.week.eq(week))
                // Checklist → ChecklistContent 조인
                .join(checklist.checklistContentList, checklistContent)
                // ChecklistContent → ChecklistMemberAnswer (대상 studyMember의 답변만 left join)
                .leftJoin(checklistContent.checklistMemberAnswerList, checklistMemberAnswer)
                .on(checklistMemberAnswer.studyMember.id.eq(studyMemberId))
                .groupBy(checklist.id, checklist.checklistCategory, checklist.checklistType)
                // HAVING: SELECT 타입인 경우에는 그룹에 응답(selectTotal)이 하나라도 있어야 함,
                // MULTIPLE 타입은 별도 조건 없이 항상 포함
                .having(new CaseBuilder()
                        .when(checklist.checklistType.eq(ChecklistType.SELECT))
                        .then(selectTotal)
                        .otherwise(Expressions.numberTemplate(Integer.class, "1"))
                        .gt(Expressions.numberTemplate(Integer.class, "0"))
                )
                .fetch();
    }

    @Override
    public List<StudyWeekChecklistResponse.ChecklistResponse> getChecklistResponses(StudyMember studyMember, int week) {

        return jpaQueryFactory
                .from(checklist)
                .join(checklist.checklistContentList, checklistContent)
                .leftJoin(checklistContent.checklistMemberAnswerList, checklistMemberAnswer)
                .on(checklistMemberAnswer.studyMember.id.eq(studyMember.getId()))
                // Roadmap을 통해 week 조건 맞추기
                .join(checklist.roadmapSemester, roadmapSemester)
                .join(roadmapSemester.roadmap, roadmap)
                .where(roadmap.week.eq(week))
                // checklistId를 기준으로 그룹화하고, 각 그룹별로 DTO를 직접 생성하여 리스트로 반환
                .transform(GroupBy.groupBy(checklist.id)
                        .list(new QStudyWeekChecklistResponse_ChecklistResponse(
                                checklist.checklistType.stringValue(),
                                checklist.title,
                                // 체크리스트 내용 목록 가져오기
                                GroupBy.list(new QStudyWeekChecklistResponse_ChecklistContentResponse(
                                        checklistContent.id,
                                        checklistContent.content,
                                        checklistMemberAnswer.checkStatus
                                ))
                        ))
                );
    }

}
