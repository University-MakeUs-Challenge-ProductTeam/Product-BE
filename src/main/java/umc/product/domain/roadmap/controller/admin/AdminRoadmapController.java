package umc.product.domain.roadmap.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.product.domain.roadmap.advisor.admin.AdminRoadmapAdviser;
import umc.product.domain.roadmap.dto.request.admin.AdminRoadmapRequest;
import umc.product.domain.roadmap.dto.response.admin.RoadmapCommonResponse;
import umc.product.global.common.base.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/web/central-admin/roadmaps")
public class AdminRoadmapController {

  private final AdminRoadmapAdviser adminRoadmapAdviser;

  @PostMapping
  @Operation(summary = "기수별 파트 로드맵 생성 API", description = "특정 기수의 파트별 로드맵을 생성하는 API입니다. 중앙 관리자만 사용 가능합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "로드맵 생성 성공")
  })
  public BaseResponse<RoadmapCommonResponse> createRoadmap(@Valid @RequestBody AdminRoadmapRequest request) {
    return BaseResponse.onSuccess(adminRoadmapAdviser.createRoadmap(request));
  }

  @PatchMapping("/{roadmapId}")
  @Operation(summary = "기수별 파트 로드맵 수정 API", description = "특정 로드맵의 week, titles를 수정하는 API입니다. 중앙 관리자만 사용 가능합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "로드맵 수정 성공")
  })
  public BaseResponse<RoadmapCommonResponse> updateRoadmap(
      @PathVariable Long roadmapId,
      @Valid @RequestBody AdminRoadmapRequest request
  ) {
    return BaseResponse.onSuccess(adminRoadmapAdviser.updateRoadmap(roadmapId, request));
  }

}
