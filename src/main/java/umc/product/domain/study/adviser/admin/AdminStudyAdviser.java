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
import umc.product.domain.study.dto.request.admin.AdminStudyModifyRequest;
import umc.product.domain.study.dto.request.admin.AdminStudyRequest;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.entity.enums.StudyType;
import umc.product.domain.study.mapper.admin.AdminStudyUniversityMapper;
import umc.product.domain.study.service.admin.*;
import umc.product.domain.study.service.member.StudyQueryService;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.domain.university.entity.University;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
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
    private final StudyQueryService studyQueryService;
    private final AdminStudyUniversityMapper adminStudyUniversityMapper;
    private final AdminStudyMemberQueryServiceImpl adminStudyMemberQueryService;

    // 스터디 생성 - 하나의 영속성으로 관리
    // todo - 최적화 필요
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

    // 스터디 수정
    // todo - 스터디 수정 로직 리팩토링 필수(계층 분리 덜 됨)
    public StudyCommonResponse modifyStudy(Long studyId, AdminStudyModifyRequest request) {
        Study study = studyQueryService.getStudy(studyId);

        // study의 studyType 변경 (request.getStudyType != null 일 때)
        StudyType newStudyType = StudyType.valueOf(request.getStudyType().toUpperCase());               // 수정할 studyType
        List<AdminStudyMemberRequest> memberRequestList = request.getMembers();                         // 수정할 사용자 요청 DTO 목록(4~5명 다 받아옴)
        List<Member> newMemberList = adminMemberService.getMemberList(memberRequestList);               // 수정할 사용자 목록(4~5명 전부)
        List<University> newUniversityList = adminStudyUniversityMapper.toUniversity(newMemberList);    // 사용자들의 대학교 목록

        // 지부 -> 교내, 교내 -> 지부 변경 시 다른 학교 인원 있는지 검증
        if (request.getStudyType() != null) {
            validateStudyType(study, newStudyType, newUniversityList);
            study.updateStudyType(newStudyType);
        }

        // 스터디에서 빠진 멤버의 StudyMember, StudyAttendance, ChecklistMemberAnswer 삭제
        List<StudyMember> removeStudyMemberList = adminStudyMemberQueryService.getRemovedStudyMemberList(study, memberRequestList);
        adminStudyMemberCommandService.deleteStudyMember(removeStudyMemberList);

        // 새로 추가된 멤버들의 SemesterPart 조회
        SemesterPart baseSemesterPart = getSemesterPart(study);
        List<AdminStudyMemberRequest> newMemberRequestList = getNewMemberList(study, memberRequestList);
        List<SemesterPart> newSemesterPartList = semesterPartService.getSemesterPartList(study, newMemberRequestList, baseSemesterPart);

        // 추가된 멤버들 StudyMember 생성
        List<StudyMember> newStudyMemberList = adminStudyMemberCommandService.createStudyMember(study, newMemberRequestList, newSemesterPartList);

        // 추가된 멤버들 StudyAttendance 생성
        adminStudyAttendanceCommandService.createStudyAttendance(newStudyMemberList);

        // 추가된 StudyMember마다 ChecklistContent에 맞는 ChecklistMemberAnswer 생성
        List<ChecklistContent> checklistContentList = adminChecklistContentQueryService.getChecklistContentList(baseSemesterPart.getSemester(), baseSemesterPart.getPart().toString());
        adminChecklistMemberAnswerCommandService.createChecklistMemberAnswer(checklistContentList, newStudyMemberList);

        // 기존 universityList와 비교해 새로운 대학교가 있으면 해당 대학교 StudyUniversity 추가
        // studyType이 SCHOOL로 수정되면 기존 학교들에서 하나의 학교로만 줄이기
        // todo - StudyUniversity 관련 작업 마무리

        return StudyCommonResponse.from(studyId);
    }

    // 스터디 타입 검증 메서드
    private static void validateStudyType(Study study, StudyType newStudyType, List<University> newUniversityList) {
        if (study.getStudyType() == StudyType.SCHOOL && newStudyType == StudyType.BRANCH) {
            // 지부 스터디로 변경 시 대학 목록이 1개면 안 됨
            if (newUniversityList.size() == 1) {
                throw new RestApiException(StudyErrorStatus.INVALID_STUDY_TYPE_BRANCH);
            }
        } else if (study.getStudyType() == StudyType.BRANCH && newStudyType == StudyType.SCHOOL) {
            // 교내 스터디로 변경 시 대학 목록이 반드시 1개여야 함
            if (newUniversityList.size() != 1) {
                throw new RestApiException(StudyErrorStatus.INVALID_STUDY_TYPE_SCHOOL);
            }
        }
    }

    // 기존 Study의 SemesterPart를 가져오는 메서드
    private static SemesterPart getSemesterPart(Study study) {
        List<StudyMember> studyMemberList = study.getStudyMemberList();
        if (studyMemberList == null || studyMemberList.isEmpty()) {
            throw new RestApiException(StudyErrorStatus.STUDY_MEMBER_NOT_FOUND);
        }
        return studyMemberList.get(0).getSemesterPart();
    }

    // 새로 추가된 멤버 요청 DTO만 가져오는 메서드
    private static List<AdminStudyMemberRequest> getNewMemberList(Study study, List<AdminStudyMemberRequest> memberRequestList) {
        // 기존 스터디원들의 memberIdList
        List<Long> originalMemberIdList = study.getStudyMemberList().stream()
                .map(sm -> sm.getSemesterPart().getMember().getId())
                .collect(Collectors.toList());

        // 새로 추가된 멤버 필터링 (이미 존재하는 StudyMember는 제외)
        return memberRequestList.stream()
                .filter(req -> !originalMemberIdList.contains(req.getMemberId()))
                .collect(Collectors.toList());
    }

    // 스터디 삭제
    public StudyCommonResponse deleteStudy(Long studyId) {
        Study study = studyQueryService.getStudy(studyId);
        adminStudyCommandService.deleteStudy(study);
        return StudyCommonResponse.from(studyId);
    }
}
