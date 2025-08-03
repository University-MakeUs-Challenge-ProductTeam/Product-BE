package umc.product.domain.checklist.service.admin;

import java.util.List;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest.ChecklistInfo;
import umc.product.domain.checklist.dto.response.admin.ChecklistCommonResponse;
import umc.product.domain.roadmap.entity.Roadmap;

public interface AdminChecklistCommandService {
  List<ChecklistCommonResponse> createChecklist(List<AdminChecklistRequest.ChecklistInfo> checklistList, Roadmap roadmap, Long semesterId);

  ChecklistCommonResponse updateChecklist(Long checklistId, ChecklistInfo request);
}
