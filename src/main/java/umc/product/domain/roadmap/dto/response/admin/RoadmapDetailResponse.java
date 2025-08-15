package umc.product.domain.roadmap.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RoadmapDetailResponse {
  @Schema(description = "로드맵 전체 제목", example = "Basic")
  private String roadmapTitle;

  @Schema(description = "파트", example = "Design")
  private String part;

  // 주차별 상세 내용을 담는 리스트
  private List<WeeklyDetail> weeklyDetails;


  @Getter
  @Builder
  @AllArgsConstructor
  public static class WeeklyDetail {
    @Schema(description = "주차", example = "1")
    private int week;

    @Schema(description = "해당 주차의 로드맵 주제 목록")
    private List<String> subjects; // RoadmapWeek에서 가져올 정보

    @Schema(description = "해당 주차의 체크리스트 목록")
    private List<ChecklistResponse> checklists; // Checklist에서 가져올 정보
  }


  @Getter
  @Builder
  @AllArgsConstructor
  public static class ChecklistResponse {
    @Schema(description = "체크리스트 카테고리", example = "키워드")
    private String category;

    @Schema(description = "체크리스트 제목(질문)", example = "키워드를 모두 채우셨나요?")
    private String title;

    @Schema(description = "체크리스트 선택지 목록", example = "[\"네, 모두 채웠어요\", \"아니요, 다 채우지 못 했어요\"]")
    private List<String> contents; // ChecklistContent에서 가져올 정보
  }

}
