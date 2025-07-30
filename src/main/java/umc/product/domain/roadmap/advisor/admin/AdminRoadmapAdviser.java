package umc.product.domain.roadmap.advisor.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.roadmap.dto.request.admin.AdminRoadmapRequest;
import umc.product.domain.roadmap.dto.response.admin.RoadmapCommonResponse;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.service.admin.AdminRoadmapCommandService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.service.SemesterService;

@Component
@RequiredArgsConstructor
public class AdminRoadmapAdviser {

  private final AdminRoadmapCommandService adminRoadmapCommandService;
  private final SemesterService semesterService;

  @Transactional
  public RoadmapCommonResponse createRoadmap(AdminRoadmapRequest request) {
    Semester semester = semesterService.getSemester(request.getSemesterId());
    Roadmap roadmap = adminRoadmapCommandService.createRoadmap(request);

    adminRoadmapCommandService.createRoadmapSemester(roadmap, semester);
    adminRoadmapCommandService.createRoadmapTitles(roadmap, request.getTitles());

    return RoadmapCommonResponse.from(roadmap.getId());
  }

}
