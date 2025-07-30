package umc.product.domain.roadmap.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "로드맵 생성, 수정, 삭제 시 공통 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class RoadmapCommonResponse {

  @Schema(description = "생성된 첫 번째 로드맵 ID", example = "1")
  private Long roadmapId;

  public static RoadmapCommonResponse from(Long roadmapId) {
    return RoadmapCommonResponse.builder()
        .roadmapId(roadmapId)
        .build();
  }

}
