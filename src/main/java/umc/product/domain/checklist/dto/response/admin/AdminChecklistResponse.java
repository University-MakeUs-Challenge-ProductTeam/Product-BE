package umc.product.domain.checklist.dto.response.admin;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.checklist.entity.enums.ChecklistCategory;
import umc.product.domain.checklist.entity.enums.ChecklistType;

@Getter
@Builder
public class AdminChecklistResponse {
  private Long id;
  private String title;
  private ChecklistType type;
  private ChecklistCategory category;
  private List<String> contents;

}
