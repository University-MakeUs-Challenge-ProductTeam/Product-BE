package umc.product.domain.study.controller.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.dto.request.member.StudyChecklistListRequest;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.domain.study.dto.response.member.StudyWeekChecklistResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

public interface StudyChecklistControllerInterface {

    @Operation(summary = "특정 주차 체크리스트 조회 API", description = "특정 주차의 체크리스트를 조회하는 API입니다. " +
            "memberId가 있으면 해당 사용자의 체크리스트를 조회하고, memberId가 없으면 로그인 한 사용자의 체크리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "체크리스트 조회 성공"
            ),
            @ApiResponse(
                    responseCode = "MEMBER404",
                    description = "회원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "MEMBER404",
							"message": "회원을 찾을 수 없습니다."
						}
						"""
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "STUDY_MEMBER404",
                    description = "해당 스터디에 속한 사용자를 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "STUDY_MEMBER404",
							"message": "해당 스터디에 속한 사용자를 찾을 수 없습니다."
						}
						"""
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "STUDY_ROADMAP404",
                    description = "해당 스터디 로드맵을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "STUDY_ROADMAP404",
							"message": "해당 스터디 로드맵을 찾을 수 없습니다."
						}
						"""
                            )
                    )
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다"),
            @Parameter(name = "week", description = "조회할 워크북 주차, path variable 입니다"),
            @Parameter(name = "memberId", description = "조회할 워크북 대상의 멤버 id, query parameter 입니다")

    })
    BaseResponse<StudyWeekChecklistResponse> getChecklist(
            @CurrentMember Member member,
            @PathVariable Long studyId,
            @PathVariable int week,
            @RequestParam(name = "memberId", required = false) Long memberId);

    @Operation(summary = "나의 특정 주차 워크북 체크리스트 입력 API", description = "나의 특정 주차 워크북 체크리스트를 입력하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "체크리스트 입력 성공"
            ),
            @ApiResponse(
                    responseCode = "STUDY_MEMBER404",
                    description = "해당 스터디에 속한 사용자를 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "STUDY_MEMBER404",
							"message": "해당 스터디에 속한 사용자를 찾을 수 없습니다."
						}
						"""
                            )
                    )
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다"),
            @Parameter(name = "week", description = "스터디 진행 주차, path variable 입니다")
    })
    BaseResponse<StudyCommonResponse> postChecklist(
            @CurrentMember Member member,
            @Valid @RequestBody StudyChecklistListRequest request,
            @PathVariable Long studyId,
            @PathVariable int week);

    @Operation(summary = "나의 특정 주차 워크북 체크리스트 수정 API", description = "나의 특정 주차 워크북 체크리스트를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "체크리스트 수정 성공"
            ),
            @ApiResponse(
                    responseCode = "STUDY_MEMBER404",
                    description = "해당 스터디에 속한 사용자를 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "STUDY_MEMBER404",
							"message": "해당 스터디에 속한 사용자를 찾을 수 없습니다."
						}
						"""
                            )
                    )
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다"),
            @Parameter(name = "week", description = "스터디 진행 주차, path variable 입니다")
    })
    BaseResponse<StudyCommonResponse> modifyChecklist(
            @CurrentMember Member member,
            @Valid @RequestBody StudyChecklistListRequest request,
            @PathVariable Long studyId,
            @PathVariable int week);
}
