package umc.product.domain.study.controller.member;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.adviser.member.StudyAdviser;
import umc.product.domain.study.dto.request.member.StudyChecklistListRequest;
import umc.product.domain.study.dto.response.member.StudyWeekChecklistResponse;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "챌린저용(앱) STUDY API", description = "챌린저용(앱) 스터디 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
@Validated
public class StudyChecklistController implements StudyChecklistControllerInterface {

    private final StudyAdviser studyAdviser;

    // 특정 주차 체크리스트 조회
    @GetMapping("/{studyId}/checklists/{week}")
    public BaseResponse<StudyWeekChecklistResponse> getChecklist(
            @CurrentMember Member member,
            @PathVariable Long studyId,
            @PathVariable int week,
            @RequestParam(name = "memberId", required = false) Long memberId) {
        // 조회 대상 사용자에 대한 분기 처리는 어드바이저 계층에서 진행
        return BaseResponse.onSuccess(studyAdviser.getStudyChecklist(member, memberId, week, studyId));
    }

    // 나의 특정 주차 워크북 체크리스트 입력
    @PostMapping("/{studyId}/checklists/{week}")
    public BaseResponse<StudyCommonResponse> postChecklist(
            @CurrentMember Member member,
            @Valid @RequestBody StudyChecklistListRequest request,
            @PathVariable Long studyId,
            @PathVariable int week) {
        return BaseResponse.onSuccess(studyAdviser.postChecklist(member, studyId, week, request));
    }

    // 나의 특정 주차 워크북 체크리스트 수정
    @PatchMapping("/{studyId}/checklists/{week}")
    public BaseResponse<StudyCommonResponse> modifyChecklist(
            @CurrentMember Member member,
            @Valid @RequestBody StudyChecklistListRequest request,
            @PathVariable Long studyId,
            @PathVariable int week) {
        // 수정할 checklistContentId 만 받기
        return BaseResponse.onSuccess(studyAdviser.modifyChecklist(member, studyId, week, request));
    }
}
