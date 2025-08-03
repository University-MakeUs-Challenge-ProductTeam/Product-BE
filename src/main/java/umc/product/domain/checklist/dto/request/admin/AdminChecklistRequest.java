package umc.product.domain.checklist.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.checklist.entity.enums.ChecklistCategory;
import umc.product.domain.checklist.entity.enums.ChecklistType;
import umc.product.domain.member.entity.enums.Part;

@Getter
@NoArgsConstructor
@Schema(description = "관리자 체크리스트 등록 요청 DTO")
public class AdminChecklistRequest {

  @Schema(description = "기수 ID", example = "8")
  @NotNull
  private Long semesterId;

  @Schema(description = "파트", example = "SPRING")
  @NotNull
  private Part part;

  @Schema(description = "주차", example = "1")
  @NotNull
  private int week;

  @Schema(description = "체크리스트 리스트")
  @NotNull
  private List<ChecklistInfo> checklist;

  @Getter
  @NoArgsConstructor
  public static class ChecklistInfo {

    @Schema(description = "체크리스트 제목", example = "오늘의 키워드를 모두 입력하셨나요?")
    private String title;

    @Schema(description = "체크리스트 타입", example = "SELECT")
    private ChecklistType type;

    @Schema(description = "체크리스트 카테고리", example = "KEYWORD")
    private ChecklistCategory category;

    @Schema(description = "체크리스트 항목 내용", example = "[\"예\", \"아니오\"]")
    private List<String> contents;
  }

}
