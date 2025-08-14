package umc.product.domain.checklist.service.admin;

import java.util.List;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest.ChecklistInfo;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistUpdateRequest;
import umc.product.domain.checklist.dto.response.admin.ChecklistCommonResponse;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;

public interface AdminChecklistCommandService {
  List<ChecklistCommonResponse> createChecklists(RoadmapSemester roadmapSemester, int week, List<ChecklistInfo> checklist);

  ChecklistCommonResponse updateChecklist(Long checklistId, AdminChecklistUpdateRequest.ChecklistUpdateInfo request, int week);
}
