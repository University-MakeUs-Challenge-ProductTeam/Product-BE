package umc.product.domain.study.adviser.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.common.MemberService;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.service.RoadmapQueryService;
import umc.product.domain.study.dto.request.StudyAttendanceRequest;
import umc.product.domain.study.dto.request.StudyModifyRequest;
import umc.product.domain.study.dto.response.StudyCommonResponse;
import umc.product.domain.study.dto.response.StudyResponse;
import umc.product.domain.study.dto.response.StudyWorkbookResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyAttendance;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.mapper.StudyMapper;
import umc.product.domain.study.service.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudyAdviser {

    private final StudyMapper studyMapper;
    private final StudyMemberQueryService studyMemberQueryService;
    private final StudyQueryService studyQueryService;
    private final StudyCommandService studyCommandService;
    private final StudyAttendanceQueryService studyAttendanceQueryService;
    private final StudyAttendanceCommandService studyAttendanceCommandService;
    private final RoadmapQueryService roadmapQueryService;
    private final MemberService memberService;

    // 스터디 정보 수정
    public StudyCommonResponse modifyStudy(Member member, StudyModifyRequest request, Long studyId) {
        StudyMember studyMember = studyMemberQueryService.getStudyMember(member, studyId);
        Study study = studyQueryService.getStudy(studyId);
        return studyCommandService.modifyStudy(studyMember, request, study);
    }

    // 스터디 참석 여부 체크
    public StudyCommonResponse checkAttendance(Member member, StudyAttendanceRequest request, Long studyId, int week) {
        // member와 studyId로 StudyMember 객체를 찾고, studyMember와 week로 특정 주차의 StudyAttendance 객체 찾기
        StudyMember studyMember = studyMemberQueryService.getStudyMember(member, studyId);
        StudyAttendance studyAttendance = studyAttendanceQueryService.getStudyAttendance(studyMember, week);

        // request에서 참석 여부를 받아 check_status 필드 변경
        return studyAttendanceCommandService.updateAttendance(studyAttendance, request, studyId);
    }

    // 스터디 정보 조회
    public StudyResponse getStudyInfo(Member member, Long studyId) {
        // 페치 조인을 사용하여 Semesterpart와 Member 미리 가져오기
        StudyMember studyMember = studyMemberQueryService.getStudyMemberFetch(member, studyId);

        // 특정 주차, 파트의 로드맵 가져오기
        List<Roadmap> roadmapList = roadmapQueryService.getRoadmapList(studyMember);

        return studyQueryService.getStudyResponse(studyMember, roadmapList);
    }

    // 주차별 워크북 정보 조회
    public StudyWorkbookResponse getStudyWorkbook(Member loginMember, Long providedMemberId, Integer providedWeek, Long studyId) {

        // memberId가 제공되면 해당 사용자, 없으면 현재 로그인한 사용자의 id로 조회
        Long targetMemberId = (providedMemberId != null) ? providedMemberId : loginMember.getId();
        Member targetMember = memberService.findById(targetMemberId );

        // week가 제공되면 해당 주차, 없으면 Study 진행 주차로 조회
        Study study = studyQueryService.getStudy(studyId);
        int week = (providedWeek != null) ? providedWeek : study.getCurrentWeek();

        // 해당 사용자와 week로 정보 조회
        StudyMember studyMember = studyMemberQueryService.getStudyMemberFetch(targetMember, studyId);

        List<String> roadmapTitleList = roadmapQueryService.getRoadmapTitleList(studyMember, week);

        return studyQueryService.getStudyWorkbookResponse(studyMember, week, roadmapTitleList);
    }
}
