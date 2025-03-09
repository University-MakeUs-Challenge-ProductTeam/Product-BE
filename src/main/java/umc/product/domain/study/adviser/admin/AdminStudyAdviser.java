package umc.product.domain.study.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.checklist.service.admin.AdminChecklistContentQueryServiceImpl;
import umc.product.domain.checklist.service.admin.AdminChecklistMemberAnswerCommandServiceImpl;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.service.SemesterPartService;
import umc.product.domain.semester.service.SemesterService;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.dto.request.admin.AdminStudyRequest;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.service.admin.AdminStudyAttendanceCommandService;
import umc.product.domain.study.service.admin.AdminStudyCommandService;
import umc.product.domain.study.service.admin.AdminStudyMemberCommandService;
import umc.product.domain.study.service.admin.AdminStudyUniversityCommandService;
import umc.product.domain.university.entity.University;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminStudyAdviser {

    private final AdminStudyCommandService adminStudyCommandService;
    private final SemesterService semesterService;
    private final AdminMemberService adminMemberService;
    private final SemesterPartService semesterPartService;
    private final AdminStudyUniversityCommandService adminStudyUniversityCommandService;
    private final AdminStudyMemberCommandService adminStudyMemberCommandService;
    private final AdminStudyAttendanceCommandService adminStudyAttendanceCommandService;
    private final AdminChecklistContentQueryServiceImpl adminChecklistContentQueryService;
    private final AdminChecklistMemberAnswerCommandServiceImpl adminChecklistMemberAnswerCommandService;

    // 스터디 생성 - 하나의 영속성으로 관리
    @Transactional
    public StudyCommonResponse createStudy(AdminStudyRequest request) {
        // Study(name, studyType, currentWeek) 생성
        Study study = adminStudyCommandService.createStudy(request);

        // SemesterPart(semesterId, part, memberId(member)) 조회
        Semester semester = semesterService.getSemester(request.getSemesterId());
        List<AdminStudyMemberRequest> memberRequestList = request.getMembers();
        List<Member> memberList = adminMemberService.getMemberList(memberRequestList);
        List<SemesterPart> semesterPartList = semesterPartService.getSemesterPartList(request.getPart(), semester, memberList);

        // StudyUniversity(study, university(memberList)) 생성
        adminStudyUniversityCommandService.createStudyUniversity(study, memberList);

        // StudyMember(semesterPart, study, studyRole) 생성
        List<StudyMember> studyMemberList = adminStudyMemberCommandService.createStudyMember(study, memberRequestList, semesterPartList);

        // StudyMember마다 StudyAttendance 생성
        adminStudyAttendanceCommandService.createStudyAttendance(studyMemberList);

        // StudyMember마다 ChecklistContent에 맞는 ChecklistMemberAnswer 생성
        List<ChecklistContent> checklistContentList = adminChecklistContentQueryService.getChecklistContentList(semester, request.getPart());
        adminChecklistMemberAnswerCommandService.createChecklistMemberAnswer(checklistContentList, studyMemberList);

        return StudyCommonResponse.from(study.getId());
    }
}
