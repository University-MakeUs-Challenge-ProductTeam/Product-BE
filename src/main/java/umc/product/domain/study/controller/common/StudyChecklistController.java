package umc.product.domain.study.controller.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.adviser.common.StudyAdviser;
import umc.product.domain.study.dto.request.StudyChecklistListRequest;
import umc.product.domain.study.dto.response.StudyWeekChecklistResponse;
import umc.product.domain.study.dto.response.StudyCommonResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "챌린저용(앱) STUDY API", description = "챌린저용(앱) 스터디 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
@Validated
public class StudyChecklistController {

    private final StudyAdviser studyAdviser;

    @GetMapping("/{studyId}/checklists/{week}")
    @Operation(summary = "특정 주차 체크리스트 조회 API", description = "특정 주차의 체크리스트를 조회하는 API입니다. " +
            "memberId가 있으면 해당 사용자의 체크리스트를 조회하고, memberId가 없으면 로그인 한 사용자의 체크리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "체크리스트 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다"),
            @Parameter(name = "week", description = "조회할 워크북 주차, path variable 입니다"),
            @Parameter(name = "memberId", description = "조회할 워크북 대상의 멤버 id, query parameter 입니다")

    })
    public BaseResponse<StudyWeekChecklistResponse> getChecklist(
            @CurrentMember Member member,
            @PathVariable Long studyId,
            @PathVariable int week,
            @RequestParam(name = "memberId", required = false) Long memberId) {
        // 조회 대상 사용자에 대한 분기 처리는 어드바이저 계층에서 진행
        return BaseResponse.onSuccess(studyAdviser.getStudyChecklist(member, memberId, week, studyId));
    }

    @PostMapping("/{studyId}/checklists/{week}")
    @Operation(summary = "나의 특정 주차 워크북 체크리스트 입력 API", description = "나의 특정 주차 워크북 체크리스트를 입력하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "체크리스트 입력 성공"
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다"),
            @Parameter(name = "week", description = "스터디 진행 주차, path variable 입니다")
    })
    public BaseResponse<StudyCommonResponse> postChecklist(
            @CurrentMember Member member,
            @Valid @RequestBody StudyChecklistListRequest request,
            @PathVariable Long studyId,
            @PathVariable int week) {
        return BaseResponse.onSuccess(studyAdviser.postChecklist(member, studyId, week, request));
    }

    @PatchMapping("/{studyId}/checklists/{week}")
    @Operation(summary = "나의 특정 주차 워크북 체크리스트 수정 API", description = "나의 특정 주차 워크북 체크리스트를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "체크리스트 수정 성공"
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다"),
            @Parameter(name = "week", description = "스터디 진행 주차, path variable 입니다")
    })
    public BaseResponse<StudyCommonResponse> modifyChecklist(
            @CurrentMember Member member,
            @Valid @RequestBody StudyChecklistListRequest request,
            @PathVariable Long studyId,
            @PathVariable int week) {
        return BaseResponse.onSuccess(studyAdviser.postChecklist(member, studyId, week, request));
    }
}
