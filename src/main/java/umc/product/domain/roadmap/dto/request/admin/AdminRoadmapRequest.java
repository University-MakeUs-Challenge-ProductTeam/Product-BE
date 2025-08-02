package umc.product.domain.roadmap.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.member.entity.enums.Part;

@Getter
@NoArgsConstructor
@Schema(description = "관리자 로드맵 등록 요청 DTO")
public class AdminRoadmapRequest {
  @Schema(description = "기수 ID", example = "8")
  @NotNull
  private Long semesterId;

  @Schema(description = "파트", example = "SPRING")
  @NotNull
  private Part part;

  @Schema(description = "주차별 워크북 제목 리스트", example = "{\"1\": [\"Figma 설치\"], \"2\": [\"기초 레이아웃\"]}")
  @NotNull
  private Map<Integer, List<String>> titlesPerWeek;


}
