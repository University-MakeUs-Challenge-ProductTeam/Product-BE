package umc.product.domain.checklist.adviser.admin;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest.ChecklistInfo;
import umc.product.domain.checklist.dto.response.admin.ChecklistCommonResponse;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.checklist.mapper.AdminChecklistMapper;
import umc.product.domain.checklist.repository.ChecklistContentRepository;
import umc.product.domain.checklist.repository.ChecklistRepository;
import umc.product.domain.checklist.service.admin.AdminChecklistCommandService;
import umc.product.domain.checklist.status.ChecklistErrorStatus;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.status.RoadmapErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Component
@RequiredArgsConstructor
public class AdminChecklistAdviser {

  private final AdminChecklistCommandService adminChecklistCommandService;
  private final RoadmapRepository roadmapRepository;
  private final ChecklistRepository checklistRepository;
  private final ChecklistContentRepository checklistContentRepository;
  private final AdminChecklistMapper adminChecklistMapper;

  @Transactional
  public List<ChecklistCommonResponse> createChecklist(AdminChecklistRequest request) {
    Roadmap roadmap = roadmapRepository.findByPartAndWeekAndSemesterId(
        request.getPart(), request.getWeek(), request.getSemesterId()
    ).orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_NOT_FOUND));

    return adminChecklistCommandService.createChecklist(request.getChecklist(), roadmap, request.getSemesterId());
  }

  @Transactional
  public ChecklistCommonResponse updateChecklist(Long checklistId, ChecklistInfo request) {
    return adminChecklistCommandService.updateChecklist(checklistId, request);
  }
}
