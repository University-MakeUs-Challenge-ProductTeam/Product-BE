package umc.product.domain.roadmap.advisor.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
  public List<RoadmapCommonResponse> createRoadmap(AdminRoadmapRequest request) {
    Semester semester = semesterService.getSemester(request.getSemesterId());
    List<RoadmapCommonResponse> responses = new ArrayList<>();

    for (Map.Entry<Integer, List<String>> entry : request.getTitlesPerWeek().entrySet()) {
      int week = entry.getKey();
      List<String> titles = entry.getValue();

      Roadmap roadmap = adminRoadmapCommandService.createRoadmap(
          request.getSemesterId(), request.getPart(), week
      );

      adminRoadmapCommandService.createRoadmapSemester(roadmap, semester);
      adminRoadmapCommandService.createRoadmapTitles(roadmap, titles);

      responses.add(RoadmapCommonResponse.from(roadmap.getId()));
    }

    return responses;
  }

  @Transactional
  public List<RoadmapCommonResponse> updateRoadmap(AdminRoadmapRequest request) {
    Semester semester = semesterService.getSemester(request.getSemesterId());

    adminRoadmapCommandService.deleteRoadmapsBySemesterAndPart(request.getSemesterId(), request.getPart());

    List<RoadmapCommonResponse> responses = new ArrayList<>();
    for (Map.Entry<Integer, List<String>> entry : request.getTitlesPerWeek().entrySet()) {
      int week = entry.getKey();
      List<String> titles = entry.getValue();

      Roadmap roadmap = adminRoadmapCommandService.createRoadmap(
          request.getSemesterId(), request.getPart(), week
      );

      adminRoadmapCommandService.createRoadmapSemester(roadmap, semester);
      adminRoadmapCommandService.createRoadmapTitles(roadmap, titles);

      responses.add(RoadmapCommonResponse.from(roadmap.getId()));
    }

    return responses;
  }
}
