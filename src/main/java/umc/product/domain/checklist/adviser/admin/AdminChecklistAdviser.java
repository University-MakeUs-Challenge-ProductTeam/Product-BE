package umc.product.domain.checklist.adviser.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.checklist.converter.admin.ChecklistConverter;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest.ChecklistInfo;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistUpdateRequest;
import umc.product.domain.checklist.dto.response.admin.AdminChecklistResponse;
import umc.product.domain.checklist.dto.response.admin.ChecklistCommonResponse;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.checklist.mapper.AdminChecklistMapper;
import umc.product.domain.checklist.repository.ChecklistContentRepository;
import umc.product.domain.checklist.repository.ChecklistRepository;
import umc.product.domain.checklist.service.admin.AdminChecklistCommandService;
import umc.product.domain.checklist.status.ChecklistErrorStatus;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.service.RoadmapQueryService;
import umc.product.domain.roadmap.status.RoadmapErrorStatus;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.service.SemesterService;
import umc.product.global.common.exception.RestApiException;

@Component
@RequiredArgsConstructor
public class AdminChecklistAdviser {

  private final AdminChecklistCommandService adminChecklistCommandService;
  private final RoadmapRepository roadmapRepository;
  private final ChecklistRepository checklistRepository;
  private final ChecklistContentRepository checklistContentRepository;
  private final AdminChecklistMapper adminChecklistMapper;
  private final SemesterService semesterService;
  private final ChecklistConverter checklistConverter;
  private final RoadmapQueryService roadmapQueryService;

  @Transactional
  public List<ChecklistCommonResponse> createChecklist(AdminChecklistRequest request) {
    RoadmapSemester roadmapSemester = roadmapQueryService.getRoadmapSemester(
        request.getSemesterId(), request.getPart()
    );

    return adminChecklistCommandService.createChecklists(
        roadmapSemester,
        request.getWeek(),
        request.getChecklist()
    );
  }

  @Transactional
  public List<ChecklistCommonResponse> updateChecklists(Long roadmapId, int week, AdminChecklistUpdateRequest request) {
    validateChecklistOwnership(roadmapId, week, request.getChecklistsToUpdate());

    List<ChecklistCommonResponse> responses = new ArrayList<>();

    for (AdminChecklistUpdateRequest.ChecklistUpdateInfo updateInfo : request.getChecklistsToUpdate()) {
      ChecklistCommonResponse response = adminChecklistCommandService.updateChecklist(
          updateInfo.getChecklistId(),
          updateInfo,
          week
      );
      responses.add(response);
    }

    return responses;
  }

  private void validateChecklistOwnership(Long roadmapId, int week, List<AdminChecklistUpdateRequest.ChecklistUpdateInfo> updateInfos) {

    Set<Long> requestedChecklistIds = updateInfos.stream()
        .map(AdminChecklistUpdateRequest.ChecklistUpdateInfo::getChecklistId)
        .collect(Collectors.toSet());

    Set<Long> validChecklistIds = checklistRepository.findAllIdsByRoadmapIdAndWeek(roadmapId, week);

    if (!validChecklistIds.containsAll(requestedChecklistIds)) {
      throw new RestApiException(ChecklistErrorStatus.CHECKLIST_OWNERSHIP_MISMATCH);
    }
  }

}
