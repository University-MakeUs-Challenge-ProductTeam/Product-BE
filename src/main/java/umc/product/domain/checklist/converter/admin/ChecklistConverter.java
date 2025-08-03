package umc.product.domain.checklist.converter.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.checklist.dto.response.admin.AdminChecklistResponse;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.checklist.entity.ChecklistContent;

@Component
@RequiredArgsConstructor
public class ChecklistConverter {

  public AdminChecklistResponse toChecklistDetailResponse(Checklist checklist) {
    return AdminChecklistResponse.builder()
        .id(checklist.getId())
        .title(checklist.getTitle())
        .type(checklist.getChecklistType())
        .category(checklist.getChecklistCategory())
        .contents(
            checklist.getChecklistContentList().stream()
                .map(ChecklistContent::getContent)
                .toList()
        )
        .build();
  }

}
