package umc.product.domain.checklist.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.checklist.entity.enums.ChecklistCategory;
import umc.product.domain.checklist.entity.enums.ChecklistType;

@Getter
@NoArgsConstructor
@Schema(description = "관리자 주차별 체크리스트 일괄 수정 요청 DTO")
public class AdminChecklistUpdateRequest {
  @Valid
  private List<ChecklistUpdateInfo> checklistsToUpdate;

  @Getter
  @NoArgsConstructor
  public static class ChecklistUpdateInfo {

    @Schema(description = "수정할 체크리스트의 ID", example = "101")
    @NotNull
    private Long checklistId;

    @Schema(description = "수정할 체크리스트 제목", example = "수정된 키워드 질문")
    private String title;

    @Schema(description = "수정할 체크리스트 타입", example = "MULTIPLE")
    private ChecklistType type;

    @Schema(description = "수정할 체크리스트 카테고리", example = "ATTENDANCE")
    private ChecklistCategory category;

    @Schema(description = "새로 대체될 체크리스트 항목 내용", example = "[\"네\", \"아니오\"]")
    private List<String> contents;
  }

}
