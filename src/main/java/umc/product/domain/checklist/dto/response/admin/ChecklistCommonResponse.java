package umc.product.domain.checklist.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChecklistCommonResponse {
  private Long checklistId;

  public static ChecklistCommonResponse from(Long id) {
    return new ChecklistCommonResponse(id);
  }
}
