package umc.product.domain.roadmap.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Getter
@Builder
@AllArgsConstructor
public class AdminRoadmapResponse {
  @Schema(description = "로드맵 ID", example = "17")
  private Long roadmapId;

  @Schema(description = "로드맵 전체 제목", example = "BASIC")
  private String title;

  @Schema(description = "파트", example = "SPRING")
  private Part part;

  @Schema(description = "주차별 주제 목록")
  private List<WeeklySubjectResponse> weeklySubjects;

  @Getter
  @Builder
  @AllArgsConstructor
  public static class WeeklySubjectResponse {
    @Schema(description = "주차", example = "1")
    private int week;

    @Schema(description = "해당 주차의 주제 목록")
    private List<String> subjects;
  }
}
