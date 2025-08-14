package umc.product.domain.checklist.service.admin;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest.ChecklistInfo;
import umc.product.domain.checklist.dto.response.admin.ChecklistCommonResponse;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.checklist.mapper.AdminChecklistMapper;
import umc.product.domain.checklist.repository.ChecklistContentRepository;
import umc.product.domain.checklist.repository.ChecklistRepository;
import umc.product.domain.checklist.status.ChecklistErrorStatus;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.repository.RoadmapSemesterRepository;
import umc.product.domain.roadmap.status.RoadmapErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Service
@RequiredArgsConstructor
public class AdminChecklistCommandServiceImpl implements AdminChecklistCommandService {
  private final ChecklistRepository checklistRepository;
  private final ChecklistContentRepository checklistContentRepository;
  private final AdminChecklistMapper adminChecklistMapper;
  private final RoadmapSemesterRepository roadmapSemesterRepository;

  @Override
  public List<ChecklistCommonResponse> createChecklists(RoadmapSemester roadmapSemester, int week, List<ChecklistInfo> checklistInfos) {
    List<ChecklistCommonResponse> responses = new ArrayList<>();

    for (ChecklistInfo dto : checklistInfos) {
      // Mapper를 호출할 때, week 도 함께 넘겨주어 Checklist 엔티티에 저장하도록
      Checklist checklist = adminChecklistMapper.toChecklistEntity(dto, roadmapSemester, week);
      checklistRepository.save(checklist);

      List<ChecklistContent> contents = dto.getContents().stream()
          .map(content -> adminChecklistMapper.toChecklistContentEntity(content, checklist))
          .toList();
      checklistContentRepository.saveAll(contents);

      responses.add(ChecklistCommonResponse.from(checklist.getId()));
    }

    return responses;
  }

  @Override
  public ChecklistCommonResponse updateChecklist(Long checklistId, ChecklistInfo request) {
    Checklist checklist = checklistRepository.findById(checklistId)
        .orElseThrow(() -> new RestApiException(ChecklistErrorStatus.CHECKLIST_NOT_FOUND));

    // 기존 content 삭제
    checklistContentRepository.deleteAllByChecklist(checklist);

    // 체크리스트 자체 업데이트
    checklist.update(request.getTitle(), request.getType(), request.getCategory());

    // 새로운 content 저장
    List<ChecklistContent> contentList = request.getContents().stream()
        .map(content -> adminChecklistMapper.toChecklistContentEntity(content, checklist))
        .toList();
    checklistContentRepository.saveAll(contentList);

    return ChecklistCommonResponse.from(checklist.getId());
  }

}
