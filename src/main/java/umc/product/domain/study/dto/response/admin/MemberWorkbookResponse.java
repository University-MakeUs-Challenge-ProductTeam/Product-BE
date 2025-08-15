package umc.product.domain.study.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberWorkbookResponse {
  @Schema(description = "멤버 닉네임", example = "델로")
  private String nickname;

  @Schema(description = "조회하는 주차", example = "1")
  private int week;

  @Schema(description = "해당 주차의 통과/아웃 상태", example = "PASS")
  private String weeklyPassStatus;

  @Schema(description = "해당 주차의 로드맵 주제 목록")
  private List<String> subjects;

  @Schema(description = "해당 주차의 상세 체크리스트 목록")
  private List<ChecklistDetail> checklists;

  // 개별 체크리스트(질문) 정보를 담는 DTO
  @Getter
  @Builder
  @AllArgsConstructor
  public static class ChecklistDetail {
    private Long checklistId;
    private String category;
    private String title;
    private List<ChecklistContentDetail> contents;
  }

  // 체크리스트의 각 항목(선택지) 정보를 담는 DTO
  @Getter
  @Builder
  @AllArgsConstructor
  public static class ChecklistContentDetail {
    private Long contentId;
    private String content;
    private boolean isChecked;
  }

}
