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
import umc.product.domain.study.dto.request.member.StudyAttendanceRequest;
import umc.product.domain.study.dto.request.member.StudyModifyRequest;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.domain.study.dto.response.member.StudyResponse;
import umc.product.domain.study.dto.response.member.StudyWorkbookResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

public interface StudyControllerInterface {

    @Operation(summary = "스터디 정보 수정 API(스터디장용)", description = "스터디장이 스터디 정보를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 정보 수정 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "스터디 이름은 비어있으면 안 됩니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "STUDY_NAME400",
							"message": "스터디 이름은 비어있으면 안 됩니다."
						}
						"""
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "스터디 리더 외에 접근할 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "COMMON403",
							"message": "금지된 요청입니다."
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
                    responseCode = "STUDY404",
                    description = "해당 스터디를 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "STUDY404",
							"message": "해당 스터디를 찾을 수 없습니다."
						}
						"""
                            )
                    )
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다")
    })
    BaseResponse<StudyCommonResponse> modifyStudy(
            @CurrentMember Member member,
            @Valid @RequestBody StudyModifyRequest request,
            @PathVariable Long studyId);

    @Operation(summary = "특정 주차 스터디 참석 여부 체크 API", description = "사용자가 특정 주차의 스터디 참석 여부를 체크하는 API입니다. (YES - 참석, NO - 불참석)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 참석 여부 체크 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 참석 여부 체크값입니다. YES, NO 중에 보내주세요.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "STUDY_ATTENDANCE400",
							"message": "유효하지 않은 참석 여부 체크값입니다. YES, NO 중에 보내주세요."
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
                    responseCode = "STUDY_ATTENDANCE404",
                    description = "해당 스터디 출석 정보를 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "STUDY_ATTENDANCE404",
							"message": "해당 스터디 출석 정보를 찾을 수 없습니다."
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
    BaseResponse<StudyCommonResponse> checkStudyAttendance(
            @CurrentMember Member member,
            @Valid @RequestBody StudyAttendanceRequest request,
            @PathVariable Long studyId,
            @PathVariable int week);

    @Operation(summary = "나의 스터디 정보 조회 API", description = "나의 스터디 정보를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 정보 조회 성공"
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
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다")
    })
    BaseResponse<StudyResponse> getMyStudy(
            @CurrentMember Member member,
            @PathVariable Long studyId);

    @Operation(summary = "주차별 워크북 조회 API", description = "워크북을 주차별로 조회하는 API입니다. memberId를 query parameter로 안 보내면 나의 워크북이 조회되고, " +
            "memberId를 query parameter로 보내면 해당 사용자의 워크북이 조회됩니다. week도 마찬가지로 query parameter로 안 보내면 스터디 진행 주차의 워크북이 조회되고, " +
            "week에 값이 담기면 해당 주차의 워크북이 조회됩니다. (체크리스트 상태가 YES면 ○, Partial이면 △, NO면 X 표기)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "워크북 조회 성공"
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
                    responseCode = "STUDY404",
                    description = "해당 스터디를 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
						{
							"timestamp": "2025-01-26T15:15:54.334Z",
							"code": "STUDY404",
							"message": "해당 스터디를 찾을 수 없습니다."
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
            @Parameter(name = "week", description = "조회할 워크북 주차, query parameter 입니다"),
            @Parameter(name = "memberId", description = "조회할 워크북 대상의 멤버 id, query parameter 입니다")
    })
    BaseResponse<StudyWorkbookResponse> getMyWorkbook(
            @CurrentMember Member member,
            @RequestParam(name = "memberId", required = false) Long memberId,
            @RequestParam(name = "week", required = false) Integer week,
            @PathVariable Long studyId);
}
