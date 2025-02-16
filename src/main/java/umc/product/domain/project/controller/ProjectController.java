package umc.product.domain.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.project.dto.request.ProjectModifyRequest;
import umc.product.domain.project.dto.request.TaskCompleteRequest;
import umc.product.domain.project.dto.response.*;
import umc.product.domain.project.service.ProjectCommandService;
import umc.product.domain.project.service.ProjectQueryService;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;
import umc.product.global.config.security.auth.PrincipalDetails;

import java.util.List;

@Tag(name = "PROJECT API", description = "프로젝트 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
@Validated
public class ProjectController {

    private final ProjectQueryService projectQueryService;
    private final ProjectCommandService projectCommandService;

    @GetMapping("/my")
    @Operation(summary = "본인 참여 프로젝트 목록 조회 API", description = "본인이 참여한 프로젝트에 대한 목록을 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로젝트 목록 조회 성공"
            )
    })
    public BaseResponse<ProjectListResponse> getMyProjects( // todo : 페이징 사용 결정 필요 - 본인이 참여한 프로젝트 개수가 페이징을 써야될 정도로 많지는 않을 것 같음
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
//            @RequestParam(name = "cursor", required = false) Long cursor,
//            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/my/{projectId}")
    @Operation(summary = "내 프로젝트 정보 조회 API", description = "본인이 참여한 프로젝트에 대한 정보를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "내 프로젝트 정보 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 id, path variable 입니다")
    })
    public BaseResponse<ProjectInfoResponse> getMyProject(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable Long projectId) {

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/{projectId}/members")
    @Operation(summary = "프로젝트 인원 리스트 조회 API", description = "프로젝트에 참여한 인원 리스트를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로젝트 인원 리스트 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 id, path variable 입니다")
    })
    public BaseResponse<List<ProjectMemberResponse>> getProjectMembers(@PathVariable Long projectId) {

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/{projectId}/tasks")
    @Operation(summary = "프로젝트 과제 리스트 조회 API", description = "프로젝트를 진행하면서 수행해야 할 과제를 조회하는 API입니다. 사용자의 파트에 맞게 1,2,3차 과제가 반환됩니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로젝트 과제 리스트 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 id, path variable 입니다")
    })
    public BaseResponse<List<ProjectTaskResponse>> getProjectTasks(
            @CurrentMember Member member,
            @PathVariable Long projectId) {
        List<ProjectTaskResponse> response = projectQueryService.getTasks(member, projectId);
        return BaseResponse.onSuccess(response);
    }

    @GetMapping("/{semesterId}")
    @Operation(summary = "기수별 프로젝트 히스토리 조회 API", description = "기수별 전체 프로젝트를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로젝트 목록 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "semesterId", description = "기수 id, path variable 입니다")
    })
    public BaseResponse<ProjectListResponse> getAllProjects(
            @PathVariable Long semesterId,
            @RequestParam(name = "cursor", required = false) Long cursor,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "프로젝트 상세 정보 조회 API", description = "프로젝트 히스토리에서 특정 프로젝트에 대한 상세 정보를 조회하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로젝트 상세 정보 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 id, path variable 입니다")
    })
    public BaseResponse<ProjectDetailResponse> getProject(
            @PathVariable Long projectId) {

        return BaseResponse.onSuccess(null);
    }

    @GetMapping("/progress")
    @Operation(summary = "메인 페이지 프로젝트 진행 상황 조회 API", description = "메인 페이지에서 현재 진행 중인 프로젝트 진행 상황을 조회하는 API입니다. 현재 날짜에 맞는 과제가 반환됩니다.")
    @ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "프로젝트 진행 상황 조회 성공"
        )
    })
    public BaseResponse<ProjectProgressResponse> getProjectProgress(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        return BaseResponse.onSuccess(null);
    }

    @PatchMapping("/{projectId}")
    @Operation(summary = "프로젝트 정보 수정 API", description = "PLAN 파트원이 프로젝트 정보를 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "프로젝트 정보 수정 성공"
            )
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 id, path variable 입니다")
    })
    public BaseResponse<ProjectModifyResponse> modifyProject(
            @Valid @RequestBody ProjectModifyRequest request,
            @PathVariable Long projectId) {

        return BaseResponse.onSuccess(null);
    }

    @PatchMapping("/{projectId}/tasks/complete")
    @Operation(summary = "과제 완료 API", description = "프로젝트 과제를 완료하는 API입니다. 과제 차수는 FIRST, SECOND, THIRD 중 보내주세요.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "과제 완료로 변경 성공"
            )
    })
    @Parameters({
            @Parameter(name = "projectId", description = "프로젝트 id, path variable 입니다")
    })
    public BaseResponse<Void> completeTask(
            @Valid @RequestBody TaskCompleteRequest request,
            @PathVariable Long projectId) {
        projectCommandService.completeTask(projectId, request);
        return BaseResponse.onSuccess(null);
    }
}