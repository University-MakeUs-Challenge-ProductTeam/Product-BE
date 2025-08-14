package umc.product.domain.checklist.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.checklist.dto.request.admin.AdminChecklistRequest;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.roadmap.entity.RoadmapSemester;

@Component
public class AdminChecklistMapper {

  public Checklist toChecklistEntity(AdminChecklistRequest.ChecklistInfo dto, RoadmapSemester roadmapSemester, int week) {
    return Checklist.builder()
        .title(dto.getTitle())
        .week(week)
        .checklistType(dto.getType())
        .checklistCategory(dto.getCategory())
        .roadmapSemester(roadmapSemester)
        .build();
  }

  public ChecklistContent toChecklistContentEntity(String content, Checklist checklist) {
    return ChecklistContent.builder()
        .content(content)
        .checklist(checklist)
        .build();
  }

}
