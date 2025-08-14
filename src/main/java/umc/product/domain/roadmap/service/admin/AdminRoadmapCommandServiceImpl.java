package umc.product.domain.roadmap.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.entity.RoadmapWeek;
import umc.product.domain.roadmap.mapper.AdminRoadmapMapper;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.repository.RoadmapSemesterRepository;
import umc.product.domain.roadmap.repository.RoadmapWeekRepository;
import umc.product.domain.semester.entity.Semester;

@Service
@RequiredArgsConstructor
public class AdminRoadmapCommandServiceImpl implements AdminRoadmapCommandService {
  private final RoadmapRepository roadmapRepository;
  private final RoadmapSemesterRepository roadmapSemesterRepository;
  private final AdminRoadmapMapper adminRoadmapMapper;
  private final RoadmapWeekRepository roadmapWeekRepository;

  @Override
  public Roadmap createRoadmap(String title, Part part) {
    Roadmap roadmap = adminRoadmapMapper.toRoadmap(title, part);
    return roadmapRepository.save(roadmap);
  }

  @Override
  public void createRoadmapSemester(Roadmap roadmap, Semester semester) {
    RoadmapSemester roadmapSemester = adminRoadmapMapper.toRoadmapSemester(roadmap, semester);
    roadmapSemesterRepository.save(roadmapSemester);
  }

  @Override
  public void createRoadmapWeek(Roadmap roadmap, int week, String subject) {
    RoadmapWeek roadmapWeek = RoadmapWeek.builder()
        .roadmap(roadmap)
        .week(week)
        .subject(subject)
        .build();

    roadmapWeekRepository.save(roadmapWeek);
  }

  @Override
  public void deleteRoadmapWeeks(Roadmap roadmap) {
    roadmapWeekRepository.deleteAllByRoadmapId(roadmap.getId());
  }

}
