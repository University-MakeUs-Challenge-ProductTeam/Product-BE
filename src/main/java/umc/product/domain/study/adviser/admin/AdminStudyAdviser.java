package umc.product.domain.study.adviser.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.checklist.entity.ChecklistMemberAnswer;
import umc.product.domain.checklist.entity.enums.ChecklistType;
import umc.product.domain.checklist.repository.ChecklistMemberAnswerRepository;
import umc.product.domain.checklist.repository.ChecklistRepository;
import umc.product.domain.checklist.service.admin.AdminChecklistContentQueryServiceImpl;
import umc.product.domain.checklist.service.admin.AdminChecklistMemberAnswerCommandServiceImpl;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.OutReason;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.member.service.admin.AdminOutService;
import umc.product.domain.member.status.AuthErrorStatus;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.entity.RoadmapWeek;
import umc.product.domain.roadmap.repository.RoadmapWeekRepository;
import umc.product.domain.roadmap.service.RoadmapQueryService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.service.SemesterPartService;
import umc.product.domain.semester.service.SemesterService;
import umc.product.domain.study.converter.member.StudyConverter;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.dto.request.admin.AdminStudyModifyRequest;
import umc.product.domain.study.dto.request.admin.AdminStudyRequest;
import umc.product.domain.study.dto.request.admin.AdminWeeklyStatusRequest;
import umc.product.domain.study.dto.response.admin.AdminStudyMemberStatusResponse;
import umc.product.domain.study.dto.response.admin.MemberWorkbookResponse;
import umc.product.domain.study.dto.response.admin.StudyInfo;
import umc.product.domain.study.dto.response.admin.WeeklyStatusCommonResponse;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.entity.WeeklyStudyStatus;
import umc.product.domain.study.entity.enums.PassStatus;
import umc.product.domain.study.entity.enums.StudyType;
import umc.product.domain.study.mapper.admin.AdminStudyUniversityMapper;
import umc.product.domain.study.repository.admin.AdminStudyRepository;
import umc.product.domain.study.repository.admin.AdminWeeklyStudyStatusRepository;
import umc.product.domain.study.repository.member.StudyMemberRepository;
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
    private final AdminStudyRepository adminStudyRepository;
    private final StudyMemberRepository studyMemberRepository;
    private final RoadmapQueryService roadmapQueryService;
    private final ChecklistRepository checklistRepository;
    private final ChecklistMemberAnswerRepository checklistMemberAnswerRepository;
    private final AdminWeeklyStudyStatusRepository adminWeeklyStudyStatusRepository;
    private final AdminOutService adminOutService;
    private final RoadmapWeekRepository roadmapWeekRepository;
    private final AdminWeeklyStudyStatusRepository weeklyStudyStatusRepository;
    private final StudyConverter studyConverter;

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

        // 멤버들이 해당 기수에 스터디가 이미 존재하면 생성이 안 되도록 제한
        adminStudyMemberQueryService.validateStudyMember(semesterPartList);

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

    @Transactional(readOnly = true)
    public Page<StudyInfo> getStudyList(Member adminMember, Long semesterId, Part part, String keyword, Pageable pageable) {
        Role adminRole = adminMember.getRole();

        if (adminRole == Role.ADMIN || adminRole == Role.CENTRAL_ADMIN) {
            // 관리자 또는 중앙 운영진: 모든 스터디 조회
            return adminStudyRepository.searchStudies(semesterId, part, keyword, null, pageable);
        } else if (adminRole == Role.SCHOOL_ADMIN) {
            // 학교 관리자: 자기 학교의 스터디만 조회
            Long universityId = adminMember.getUniversity().getId();
            return adminStudyRepository.searchStudies(semesterId, part, keyword, universityId, pageable);
        } else {
            // 그 외의 역할은 접근 권한 없음
            throw new RestApiException(AuthErrorStatus.INVALID_ROLE);
        }
    }

    public AdminStudyMemberStatusResponse getStudyMembersStatus(Long studyId) {
        // 1. 기준이 되는 Study와, 해당 스터디에 속한 모든 StudyMember를 조회합 (N+1 방지 위해 fetch join 사용)
        Study study = studyQueryService.getStudy(studyId);
        List<StudyMember> studyMembers = studyMemberRepository.findAllByStudyFetch(study);

        // 2. 스터디의 Roadmap 구조(총 몇 주차인지, 각 주차별 체크리스트 구성)를 조회합니다.
        Roadmap roadmap = roadmapQueryService.getRoadmapByStudy(study);
        RoadmapSemester roadmapSemester = roadmapQueryService.getRoadmapSemesterByRoadmapAndStudy(roadmap, study);
        List<Checklist> allChecklists = checklistRepository.findAllByRoadmapSemester(roadmapSemester);

        // 3. 이 스터디의 '모든' 멤버가 제출한 '모든' 답변(ChecklistMemberAnswer)을 한 번의 쿼리로 가져옵니다.
        List<ChecklistMemberAnswer> allAnswers = checklistMemberAnswerRepository.findAllByStudyMembers(studyMembers);

        // 4. 조회한 데이터를 가공하여 최종 DTO를 조립합니다.
        List<AdminStudyMemberStatusResponse.MemberStatusInfo> memberStatusInfos =
            buildMemberStatusInfos(study, studyMembers, allChecklists, allAnswers);

        return AdminStudyMemberStatusResponse.builder()
            .members(memberStatusInfos)
            .build();
    }

    private List<AdminStudyMemberStatusResponse.MemberStatusInfo> buildMemberStatusInfos(
        Study study, List<StudyMember> studyMembers, List<Checklist> allChecklists, List<ChecklistMemberAnswer> allAnswers) {

        // 로드맵의 총 주차 수 계산
        int totalWeeks = allChecklists.stream().mapToInt(Checklist::getWeek).max().orElse(0);
        int currentWeek = study.getCurrentWeek();

        Map<Long, List<ChecklistMemberAnswer>> answersByMemberId = allAnswers.stream()
            .collect(Collectors.groupingBy(answer -> answer.getStudyMember().getId()));
        Map<Integer, List<Checklist>> checklistsByWeek = allChecklists.stream()
            .collect(Collectors.groupingBy(Checklist::getWeek));

        return studyMembers.stream().map(member -> {
            List<AdminStudyMemberStatusResponse.WeeklyChecklistStatus> weeklyStatuses = new ArrayList<>();
            List<ChecklistMemberAnswer> memberAnswers = answersByMemberId.getOrDefault(member.getId(), Collections.emptyList());

            // 1주차부터 마지막 주차까지 순회하며 상태 계산합니다.
            for (int week = 1; week <= totalWeeks; week++) {
                String status;
                if (week > currentWeek) {
                    // 요구사항: 미래 주차는 "-" 로 표기
                    status = "FUTURE";
                } else {
                    // 과거 또는 현재 주차는 상태를 계산합니다.
                    status = calculateWeeklyStatus(memberAnswers, checklistsByWeek.getOrDefault(week, Collections.emptyList()));
                }
                weeklyStatuses.add(new AdminStudyMemberStatusResponse.WeeklyChecklistStatus(week, status));
            }

            return AdminStudyMemberStatusResponse.MemberStatusInfo.builder()
                .studyMemberId(member.getId())
                .memberName(member.getSemesterPart().getMember().getName())
                .nickname(member.getSemesterPart().getMember().getNickName())
                .part(member.getSemesterPart().getPart().name())
                .roleInStudy(member.getStudyRole().getName()) // "스터디 리더", "스터디원"
                .weeklyChecklistStatuses(weeklyStatuses)
                .build();
        }).collect(Collectors.toList());
    }

    /**
     * 한 멤버의 한 주차에 대한 모든 답변과 체크리스트 구조를 바탕으로,
     * 해당 주차의 최종 상태(YES, NO, PARTIAL)를 계산하는 메서드
     */
    private String calculateWeeklyStatus(List<ChecklistMemberAnswer> memberAnswersForTotal, List<Checklist> checklistsForWeek) {
        // 1. 사전 조건 확인
        if (checklistsForWeek.isEmpty()) {
            return "NO_CHECKLIST"; // 해당 주차에 체크리스트가 없는 경우
        }

        // 이 멤버가 이번 주에 답변한 내용만 필터링
        Set<Long> checklistIdsForWeek = checklistsForWeek.stream().map(Checklist::getId).collect(Collectors.toSet());
        List<ChecklistMemberAnswer> memberAnswersForWeek = memberAnswersForTotal.stream()
            .filter(answer -> checklistIdsForWeek.contains(answer.getChecklistContent().getChecklist().getId()))
            .toList();

        if (memberAnswersForWeek.isEmpty()) {
            return "NO"; // 체크리스트는 있지만 답변이 하나도 없는 경우
        }

        // 2. 각 체크리스트별 상태를 계산하여 리스트에 담기
        List<String> individualStatuses = checklistsForWeek.stream()
            .map(checklist -> calculateSingleChecklistStatus(checklist, memberAnswersForWeek))
            .toList();

        // 3. 계산된 개별 상태들을 종합하여 주차의 최종 상태를 결정
        if (individualStatuses.contains("PARTIAL") || (individualStatuses.contains("YES") && individualStatuses.contains("NO"))) {
            return "PARTIAL"; // 하나라도 PARTIAL이 있거나, YES와 NO가 섞여있으면 PARTIAL
        } else if (individualStatuses.stream().allMatch(s -> s.equals("YES"))) {
            return "YES"; // 모든 체크리스트가 YES이면 YES
        } else {
            return "NO"; // 그 외 (전부 NO이거나 답변이 없는 경우 등)
        }
    }

    /**
     * 개별 체크리스트 하나의 상태를 계산하는 헬퍼 메서드
     */
    private String calculateSingleChecklistStatus(Checklist checklist, List<ChecklistMemberAnswer> memberAnswersForWeek) {
        // 이 체크리스트에 해당하는 답변들만 필터링
        Set<Long> contentIds = checklist.getChecklistContentList().stream().map(ChecklistContent::getId).collect(Collectors.toSet());
        List<ChecklistMemberAnswer> relevantAnswers = memberAnswersForWeek.stream()
            .filter(answer -> contentIds.contains(answer.getChecklistContent().getId()))
            .toList();

        if (relevantAnswers.isEmpty()) {
            return "NO";
        }

        // SELECT 타입 상태 계산
        if (checklist.getChecklistType() == ChecklistType.SELECT) {
            boolean hasPositiveAnswer = relevantAnswers.stream()
                .anyMatch(answer -> answer.isCheckStatus() &&
                    (answer.getChecklistContent().getContent().equals("네, 참석했어요") ||
                        answer.getChecklistContent().getContent().equals("네, 모두 채웠어요")));
            if (hasPositiveAnswer) return "YES";

            boolean hasNegativeAnswer = relevantAnswers.stream()
                .anyMatch(answer -> answer.isCheckStatus() &&
                    (answer.getChecklistContent().getContent().equals("아니요, 참석하지 못 했어요") ||
                        answer.getChecklistContent().getContent().equals("아니요, 다 채우지 못 했어요")));
            if (hasNegativeAnswer) return "NO";

            // MULTIPLE 타입 상태 계산
        } else if (checklist.getChecklistType() == ChecklistType.MULTIPLE) {
            long totalOptions = checklist.getChecklistContentList().size();
            long checkedCount = relevantAnswers.stream().filter(ChecklistMemberAnswer::isCheckStatus).count();

            if (checkedCount == 0) return "NO";
            if (checkedCount == totalOptions) return "YES";
            return "PARTIAL";
        }

        return "NO"; // 기본값은 NO
    }


    @Transactional
    public WeeklyStatusCommonResponse setWeeklyStatus(Long studyMemberId, int week, AdminWeeklyStatusRequest request) {
        // 1. 대상이 되는 StudyMember 조회
        StudyMember studyMember = studyMemberRepository.findById(studyMemberId)
            .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_MEMBER_NOT_FOUND));

        // 2. 해당 멤버/주차의 WeeklyStudyStatus를 찾거나 없으면 새로 생성
        WeeklyStudyStatus weeklyStatus = adminWeeklyStudyStatusRepository
            .findByStudyMemberAndWeek(studyMember, week)
            .orElse(WeeklyStudyStatus.builder()
                .studyMember(studyMember)
                .week(week)
                .build());

        weeklyStatus.updateStatus(request.getStatus());
        adminWeeklyStudyStatusRepository.save(weeklyStatus);

        // 3. 만약 설정된 상태가 'OUT'이라면, 'MemberOut' 경고를 부여
        if (request.getStatus() == PassStatus.OUT) {
            Member member = studyMember.getSemesterPart().getMember();

            // '스터디 불이행' 사유로 MemberOut을 생성하는 서비스를 호출
            // 이 서비스 내부에서 3회 누적 시 최종 OUT 처리 로직이 동작
            adminOutService.postMemberOut(member, OutReason.STUDY_CHECK_NOT_PERFORM);
        }
        return WeeklyStatusCommonResponse.builder()
            .weeklyStatusId(weeklyStatus.getId())
            .build();
    }

    @Transactional(readOnly = true)
    public MemberWorkbookResponse getMemberWorkbook(Long studyMemberId, int week) {
        // 1. PathVariable로 받은 studyMemberId로 StudyMember를 즉시 조회합니다.
        StudyMember studyMember = studyMemberRepository.findWithDetailsById(studyMemberId) // fetch join하는 메서드로 가정
            .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_MEMBER_NOT_FOUND));

        // 2. studyMember를 통해 Study, Roadmap, RoadmapSemester 등을 찾아냅니다.
        Study study = studyMember.getStudy();
        Roadmap roadmap = roadmapQueryService.getRoadmapByStudy(study);
        RoadmapSemester roadmapSemester = roadmapQueryService.getRoadmapSemesterByRoadmapAndStudy(roadmap, study);

        // 3. 이하 로드맵 주제, 체크리스트, 답변, 주차별 상태를 조회하는 로직은 이전과 동일합니다.
        List<RoadmapWeek> roadmapWeeks = roadmapWeekRepository.findAllByRoadmapAndWeek(roadmap, week);
        List<Checklist> checklists = checklistRepository.findAllByRoadmapSemesterAndWeekFetch(roadmapSemester, week);
        List<ChecklistMemberAnswer> memberAnswers = checklistMemberAnswerRepository.findAllByStudyMemberAndWeek(studyMember, week);
        Optional<WeeklyStudyStatus> weeklyStatus = weeklyStudyStatusRepository.findByStudyMemberAndWeek(studyMember, week);

        // 4. 조회한 모든 데이터를 컨버터에 넘겨 최종 DTO로 조립합니다.
        return studyConverter.toMemberWorkbookResponse(studyMember, week, weeklyStatus.orElse(null), roadmapWeeks, checklists, memberAnswers);
    }
}
