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
import umc.product.domain.study.dto.common.request.StudyAttendanceRequest;
import umc.product.domain.study.dto.common.request.StudyModifyRequest;
import umc.product.domain.study.dto.common.response.StudyCommonResponse;
import umc.product.domain.study.dto.common.response.StudyResponse;
import umc.product.domain.study.dto.common.response.StudyWorkbookResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "챌린저용(앱) STUDY API", description = "챌린저용(앱) 스터디 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/studies")
@Validated
public class StudyController {

    private final StudyAdviser studyAdviser;

    @PatchMapping("/{studyId}")
    @Operation(summary = "스터디 정보 수정 API(스터디장용)", description = "스터디장이 스터디 정보를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 정보 수정 성공"
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다")
    })
    public BaseResponse<StudyCommonResponse> modifyStudy(
            @CurrentMember Member member,
            @Valid @RequestBody StudyModifyRequest request,
            @PathVariable Long studyId) {
        return BaseResponse.onSuccess(studyAdviser.modifyStudy(member, request, studyId));
    }

    @PostMapping("/{studyId}/attendances/{week}")
    @Operation(summary = "특정 주차 스터디 참석 여부 체크 API", description = "사용자가 특정 주차의 스터디 참석 여부를 체크하는 API입니다. (YES - 참석, NO - 불참석)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 참석 여부 체크 성공"
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다"),
            @Parameter(name = "week", description = "스터디 진행 주차, path variable 입니다")
    })
    public BaseResponse<StudyCommonResponse> checkStudyAttendance(
            @CurrentMember Member member,
            @Valid @RequestBody StudyAttendanceRequest request,
            @PathVariable Long studyId,
            @PathVariable int week) {
        return BaseResponse.onSuccess(studyAdviser.checkAttendance(member, request, studyId, week));
    }

    @GetMapping("/{studyId}")
    @Operation(summary = "나의 스터디 정보 조회 API", description = "나의 스터디 정보를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 정보 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다")
    })
    public BaseResponse<StudyResponse> getMyStudy(
            @CurrentMember Member member,
            @PathVariable Long studyId) {
        return BaseResponse.onSuccess(studyAdviser.getStudyInfo(member, studyId));
    }

    @GetMapping("/{studyId}/workbooks")
    @Operation(summary = "주차별 워크북 조회 API", description = "워크북을 주차별로 조회하는 API입니다. memberId를 query parameter로 안 보내면 나의 워크북이 조회되고, " +
            "memberId를 query parameter로 보내면 해당 사용자의 워크북이 조회됩니다. week도 마찬가지로 query parameter로 안 보내면 스터디 진행 주차의 워크북이 조회되고, " +
            "week에 값이 담기면 해당 주차의 워크북이 조회됩니다. (체크리스트 상태가 YES면 ○, Partial이면 △, NO면 X 표기)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "워크북 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다"),
            @Parameter(name = "week", description = "조회할 워크북 주차, query parameter 입니다"),
            @Parameter(name = "memberId", description = "조회할 워크북 대상의 멤버 id, query parameter 입니다")
    })
    public BaseResponse<StudyWorkbookResponse> getMyWorkbook(
            @CurrentMember Member member,
            @RequestParam(name = "memberId", required = false) Long memberId,
            @RequestParam(name = "week", required = false) Integer week,
            @PathVariable Long studyId) {

        // memberId가 존재하면, 해당 멤버의 워크북 조회 로직을 처리하고. memberId가 없으면, 현재 로그인한 member의 워크북 조회 로직을 처리
        // week가 존재하면, 해당 주차의 워크북을 조회하고, week가 없다면 스터디 진행 주차의 워크북이 조회
        // 컨트롤러 계층에서 Study의 currentWeek 값을 못 가져오므로, 어드바이저 계층에서 분기 처리
        return BaseResponse.onSuccess(studyAdviser.getStudyWorkbook(member, memberId, week, studyId));
    }
}
