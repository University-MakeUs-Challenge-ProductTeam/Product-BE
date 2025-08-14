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
import umc.product.domain.roadmap.service.RoadmapQueryService;
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
  private final RoadmapQueryService roadmapQueryService;

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
  public RoadmapCommonResponse updateRoadmap(Long roadmapId, AdminRoadmapRequest request) {
    // 기존 Roadmap 조회
    Roadmap roadmap = roadmapQueryService.getRoadmapById(roadmapId);

    // 부모 Roadmap 정보 수정
    roadmap.update(request.getTitle(), request.getPart());

    // 기존 RoadmapWeek들 모두 삭제
    adminRoadmapCommandService.deleteRoadmapWeeks(roadmap);

    // 새로운 RoadmapWeek들 생성
    for (AdminRoadmapRequest.RoadmapWeekRequest weekRequest : request.getWeeklySubjects()) {
      int week = weekRequest.getWeek();
      List<String> subjects = weekRequest.getSubjects();

      for (String subject : subjects) {
        adminRoadmapCommandService.createRoadmapWeek(roadmap, week, subject);
      }
    }

    return RoadmapCommonResponse.from(roadmap.getId());
  }

  @Transactional(readOnly = true)
  public List<AdminRoadmapResponse> getRoadmaps(Long semesterId, Part part) {
    List<Roadmap> roadmaps = roadmapRepository.findAllBySemesterIdAndPart(semesterId, part);

    return roadmaps.stream()
        .map(roadmapConverter::toRoadmapDetailResponse)
        .toList();
  }
}
