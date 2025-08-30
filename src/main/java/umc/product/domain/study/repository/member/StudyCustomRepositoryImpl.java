package umc.product.domain.study.repository.member;

import static umc.product.domain.university.entity.QUniversity.university;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.checklist.entity.QChecklist;
import umc.product.domain.checklist.entity.QChecklistContent;
import umc.product.domain.checklist.entity.QChecklistMemberAnswer;
import umc.product.domain.checklist.entity.enums.ChecklistType;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.entity.QRoadmap;
import umc.product.domain.roadmap.entity.QRoadmapSemester;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.repository.RoadmapSemesterRepository;
import umc.product.domain.semester.entity.QSemesterPart;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.study.dto.response.member.*;
import umc.product.domain.study.entity.*;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StudyCustomRepositoryImpl implements StudyCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final RoadmapRepository roadmapRepository;
    private final RoadmapSemesterRepository roadmapSemesterRepository;

    private final QStudyMember studyMember = QStudyMember.studyMember;
    private final QMember member = QMember.member;
    private final QStudyAttendance studyAttendance = QStudyAttendance.studyAttendance;
    private final QSemesterPart semesterPart = QSemesterPart.semesterPart;
    private final QChecklist checklist = QChecklist.checklist;
    private final QChecklistContent checklistContent = QChecklistContent.checklistContent;
    private final QChecklistMemberAnswer checklistMemberAnswer = QChecklistMemberAnswer.checklistMemberAnswer;
    private final QRoadmapSemester roadmapSemester = QRoadmapSemester.roadmapSemester;
    private final QRoadmap roadmap = QRoadmap.roadmap;
    private final QStudy study = QStudy.study;

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
    public List<StudyWorkbookResponse.StudyChecklistResponse> getStudyChecklists(Long studyMemberId, RoadmapSemester roadmapSemester, int week) {

        // 체크리스트 입력 전 -> checkStatus가 전부 다 false -> 빈 리스트 반환
        Long totalTrueCount = validateCheckStatus(studyMemberId, roadmapSemester, week);
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
                // Checklist → ChecklistContent 조인
                .join(checklist.checklistContentList, checklistContent)
                // ChecklistContent → ChecklistMemberAnswer (대상 studyMember의 답변만 left join)
                .leftJoin(checklistContent.checklistMemberAnswerList, checklistMemberAnswer)
                .on(checklistMemberAnswer.studyMember.id.eq(studyMemberId))

                .where(
                    checklist.roadmapSemester.eq(roadmapSemester), // 1. 부모(RoadmapSemester)가 같은지 확인
                    checklist.week.eq(week)                        // 2. Checklist 자체의 week가 같은지 확인
                )
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
    public List<StudyWeekChecklistResponse.ChecklistResponse> getChecklistResponses(Long studyMemberId, RoadmapSemester roadmapSemester, int week) {
        return jpaQueryFactory
            .from(checklist)
            .join(checklist.checklistContentList, checklistContent)
            .leftJoin(checklistContent.checklistMemberAnswerList, checklistMemberAnswer)
            .on(checklistMemberAnswer.studyMember.id.eq(studyMemberId))
            .where(
                checklist.roadmapSemester.eq(roadmapSemester), // 1. 상위 서비스가 찾아준 RoadmapSemester와 같은지 확인
                checklist.week.eq(week)                        // 2. Checklist 자체의 week가 같은지 확인
            )
            // 이하 그룹화 및 DTO 변환 로직은 이전과 동일합니다.
            .transform(GroupBy.groupBy(checklist.id)
                .list(new QStudyWeekChecklistResponse_ChecklistResponse(
                    checklist.checklistType.stringValue(),
                    checklist.title,
                    GroupBy.list(new QStudyWeekChecklistResponse_ChecklistContentResponse(
                        checklistContent.id,
                        checklistContent.content,
                        checklistMemberAnswer.checkStatus
                    ))
                ))
            );
    }

    @Override
    public boolean getPostStatus(StudyMember studyMember, int week) {
        Semester semester = studyMember.getSemesterPart().getSemester();
        Part part = studyMember.getSemesterPart().getPart();

        Optional<Roadmap> roadmapOpt = roadmapRepository.findBySemesterIdAndPart(semester.getId(), part);
        if (roadmapOpt.isEmpty()) {
            // 로드맵이 없다는 것은 아직 체크리스트가 하나도 없다는 의미이므로, 게시글 상태는 false
            return false;
        }

        Roadmap roadmap = roadmapOpt.get();

        Optional<RoadmapSemester> roadmapSemesterOpt = roadmapSemesterRepository.findByRoadmapAndSemester_Id(roadmap, semester.getId());
        if (roadmapSemesterOpt.isEmpty()) {
            return false;
        }
        RoadmapSemester roadmapSemester = roadmapSemesterOpt.get();

        // 4. 찾아낸 roadmapSemester를 파라미터로 넘겨주어 checkStatus를 검증
        Long totalTrueCount = validateCheckStatus(studyMember.getId(), roadmapSemester, week);

        return totalTrueCount != null && totalTrueCount != 0;
    }

    // 체크리스트 checkStatus 검증
    private Long validateCheckStatus(Long studyMemberId, RoadmapSemester roadmapSemester, int week) {
        return jpaQueryFactory
                .select(checklistMemberAnswer.id.count())
                .from(checklist)
                .where(
                    checklist.roadmapSemester.eq(roadmapSemester),
                    checklist.week.eq(week)
                )
                .join(checklist.checklistContentList, checklistContent)
                .leftJoin(checklistContent.checklistMemberAnswerList, checklistMemberAnswer)
                .on(checklistMemberAnswer.studyMember.id.eq(studyMemberId)
                        .and(checklistMemberAnswer.checkStatus.eq(true)))
                .fetchOne();
    }

    // 스터디 참여 멤버 닉네임 리스트 조회
    @Override
    public List<StudyInfoResponse> getStudyInfoList(Member member) {
        QStudyMember allStudyMember = new QStudyMember("allStudyMember");
        QSemesterPart allSemesterPart = new QSemesterPart("allSemesterPart");
        QMember participant = new QMember("participant");

        return jpaQueryFactory
                .from(semesterPart)
                .join(semesterPart.studyMemberList, studyMember)
                .join(studyMember.study, study)
                // leftjoin -> 모든 StudyMember 조회
                .leftJoin(study.studyMemberList, allStudyMember)
                .leftJoin(allStudyMember.semesterPart, allSemesterPart)
                .leftJoin(allSemesterPart.member, participant)
                // 로그인한 멤버의 SemesterPart
                .where(semesterPart.member.eq(member))
                .transform(GroupBy.groupBy(study.id)
                        .list(new QStudyInfoResponse(
                                study.id,
                                semesterPart.semester.name,
                                study.studyType.stringValue(),
                                semesterPart.part.stringValue(),
                                study.name,
                                GroupBy.list(participant.nickName)
                        ))
                );
    }

    @Override
    public StudyMemberResponse getSingleStudyMember(Long studyMemberId, int targetWeek) {
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
            .join(member.university, university)
            // 출석 정보를 위해 LEFT JOIN을 사용
            .leftJoin(studyAttendance)
            .on(studyAttendance.studyMember.id.eq(studyMemberId)
                .and(studyAttendance.week.eq(targetWeek)))
            .where(studyMember.id.eq(studyMemberId))
            .fetchOne();
    }

}
