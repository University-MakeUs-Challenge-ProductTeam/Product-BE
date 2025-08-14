package umc.product.domain.roadmap.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.advisor.admin.AdminRoadmapAdviser;
import umc.product.domain.roadmap.dto.request.admin.AdminRoadmapRequest;
import umc.product.domain.roadmap.dto.response.admin.AdminRoadmapListResponse;
import umc.product.domain.roadmap.dto.response.admin.AdminRoadmapResponse;
import umc.product.domain.roadmap.dto.response.admin.RoadmapCommonResponse;
import umc.product.domain.roadmap.dto.response.admin.RoadmapInfo;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "관리자용(웹) ROADMAP API", description = "관리자용(웹) 로드맵 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/roadmaps")
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
  @Operation(summary = "기수별 파트 로드맵 수정 API", description = "ID로 특정 로드맵의 내용을 수정하는 API입니다. 중앙 관리자만 사용 가능합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "로드맵 수정 성공")
  })
  public BaseResponse<RoadmapCommonResponse> updateRoadmap(@PathVariable Long roadmapId, @Valid @RequestBody AdminRoadmapRequest request) {
    return BaseResponse.onSuccess(adminRoadmapAdviser.updateRoadmap(roadmapId, request));
  }

  @GetMapping
  @Operation(summary = "로드맵 조회 API", description = "기수와 파트로 로드맵을 조회합니다. 이 조회 API는 로드맵 생성 시 '이전 기수 로드맵 불러오기' 사용할 때를 위한 API 입니다.")
  @ApiResponse(responseCode = "200", description = "조회 성공")
  public BaseResponse<AdminRoadmapResponse> getRoadmap(
      @RequestParam Long semesterId,
      @RequestParam Part part
  ) {
    return BaseResponse.onSuccess(adminRoadmapAdviser.getRoadmap(semesterId, part));
  }

  @GetMapping("/list")
  @Operation(summary = "로드맵 목록 조회 API", description = "전체 로드맵 목록을 조회하고 페이징 처리합니다.")
  public BaseResponse<AdminRoadmapListResponse> getRoadmapList(
      @RequestParam(required = false) Long semesterId,
      @RequestParam(required = false) Part part,
      @RequestParam(required = false) String keyword,
      @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    Page<RoadmapInfo> roadmapPage = adminRoadmapAdviser.getRoadmapList(semesterId, part, keyword, pageable);
    return BaseResponse.onSuccess(AdminRoadmapListResponse.from(roadmapPage));
  }

}
