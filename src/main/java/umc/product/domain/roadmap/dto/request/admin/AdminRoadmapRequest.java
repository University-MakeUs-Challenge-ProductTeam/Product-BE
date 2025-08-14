package umc.product.domain.roadmap.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

  @Schema(description = "로드맵 제목", example = "BASIC")
  @NotBlank
  private String title;

  @Schema(description = "주차별 주제 목록")
  @Valid
  private List<RoadmapWeekRequest> weeklySubjects;

  @Getter
  @Schema(description = "주차별 주제 요청 DTO")
  public static class RoadmapWeekRequest {

    @Schema(description = "주차", example = "1")
    @NotNull
    private Integer week;

    @Schema(description = "해당 주차의 주제 목록", example = "[\"Figma 기본기\", \"Prototyping의 이해\"]")
    private List<String> subjects;
  }

}
