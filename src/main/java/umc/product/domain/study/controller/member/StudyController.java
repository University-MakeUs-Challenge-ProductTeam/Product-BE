package umc.product.domain.study.controller.member;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.adviser.member.StudyAdviser;
import umc.product.domain.study.dto.request.member.StudyAttendanceRequest;
import umc.product.domain.study.dto.request.member.StudyModifyRequest;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.domain.study.dto.response.member.StudyResponse;
import umc.product.domain.study.dto.response.member.StudyWorkbookResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "챌린저용(앱) STUDY API", description = "챌린저용(앱) 스터디 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
@Validated
public class StudyController implements StudyControllerInterface {

    private final StudyAdviser studyAdviser;

    // 스터디 정보 수정(스터디장용)
    @PatchMapping("/{studyId}")
    public BaseResponse<StudyCommonResponse> modifyStudy(
            @CurrentMember Member member,
            @Valid @RequestBody StudyModifyRequest request,
            @PathVariable Long studyId) {
        // 스터디명, 스터디 현재 주자만 수정 가능
        return BaseResponse.onSuccess(studyAdviser.modifyStudy(member, request, studyId));
    }

    // 특정 주차 스터디 참석 여부 체크
    @PostMapping("/{studyId}/attendances/{week}")
    public BaseResponse<StudyCommonResponse> checkStudyAttendance(
            @CurrentMember Member member,
            @Valid @RequestBody StudyAttendanceRequest request,
            @PathVariable Long studyId,
            @PathVariable int week) {
        return BaseResponse.onSuccess(studyAdviser.checkAttendance(member, request, studyId, week));
    }

    // 나의 스터디 정보 조회
    @GetMapping("/{studyId}")
    public BaseResponse<StudyResponse> getMyStudy(
            @CurrentMember Member member,
            @PathVariable Long studyId) {
        return BaseResponse.onSuccess(studyAdviser.getStudyInfo(member, studyId));
    }

    // 주차별 워크북 조회
    @GetMapping("/{studyId}/workbooks")
    public BaseResponse<StudyWorkbookResponse> getMyWorkbook(
            @CurrentMember Member member,
            @RequestParam(name = "memberId", required = false) Long memberId,
            @RequestParam(name = "week", required = false) Integer week,
            @PathVariable Long studyId) {

        // memberId가 존재하면, 해당 멤버의 워크북 조회 로직을 처리하고. memberId가 없으면, 현재 로그인한 member의 워크북 조회 로직을 처리
        // week가 존재하면, 해당 주차의 워크북을 조회하고, week가 없다면 스터디 진행 주차의 워크북이 조회
        return BaseResponse.onSuccess(studyAdviser.getStudyWorkbook(member, memberId, week, studyId));
    }
}
