package umc.product.domain.roadmap.advisor.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.converter.admin.RoadmapConverter;
import umc.product.domain.roadmap.dto.request.admin.AdminRoadmapRequest;
import umc.product.domain.roadmap.dto.response.admin.AdminRoadmapResponse;
import umc.product.domain.roadmap.dto.response.admin.RoadmapCommonResponse;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.service.admin.AdminRoadmapCommandService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.service.SemesterService;

@Component
@RequiredArgsConstructor
public class AdminRoadmapAdviser {

  private final AdminRoadmapCommandService adminRoadmapCommandService;
  private final SemesterService semesterService;
  private final RoadmapRepository roadmapRepository;
  private final RoadmapConverter roadmapConverter;

  @Transactional
  public RoadmapCommonResponse createRoadmap(AdminRoadmapRequest request) {
    Semester semester = semesterService.getSemester(request.getSemesterId());

    Roadmap roadmap = adminRoadmapCommandService.createRoadmap(request.getTitle(), request.getPart());

    adminRoadmapCommandService.createRoadmapSemester(roadmap, semester);

    for (AdminRoadmapRequest.RoadmapWeekRequest weekRequest : request.getWeeklySubjects()) {
      int week = weekRequest.getWeek();
      List<String> subjects = weekRequest.getSubjects();

      // 한 주차에 여러 주제(subject)가 있을 수 있으므로, 중첩 반복문을 사용
      for (String subject : subjects) {
        // 부모 roadmap, 주차, 개별 주제를 전달하여 RoadmapWeek를 생성하고 저장
        adminRoadmapCommandService.createRoadmapWeek(roadmap, week, subject);
      }
    }
    return RoadmapCommonResponse.from(roadmap.getId());
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

  @Transactional(readOnly = true)
  public List<AdminRoadmapResponse> getRoadmaps(Long semesterId, Part part) {
    List<Roadmap> roadmaps = roadmapRepository.findAllBySemesterIdAndPart(semesterId, part);

    return roadmaps.stream()
        .map(roadmapConverter::toRoadmapDetailResponse)
        .toList();
  }
}
