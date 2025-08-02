package umc.product.domain.checklist.adviser.admin;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest;
import umc.product.domain.checklist.dto.response.admin.ChecklistCommonResponse;
import umc.product.domain.checklist.service.admin.AdminChecklistCommandService;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.status.RoadmapErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Component
@RequiredArgsConstructor
public class AdminChecklistAdviser {

  private final AdminChecklistCommandService adminChecklistCommandService;
  private final RoadmapRepository roadmapRepository;

  @Transactional
  public List<ChecklistCommonResponse> createChecklist(AdminChecklistRequest request) {
    Roadmap roadmap = roadmapRepository.findByPartAndWeekAndSemesterId(
        request.getPart(), request.getWeek(), request.getSemesterId()
    ).orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_NOT_FOUND));

    return adminChecklistCommandService.createChecklist(request.getChecklist(), roadmap, request.getSemesterId());
  }
}
