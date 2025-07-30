package umc.product.domain.roadmap.service.admin;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.roadmap.dto.request.admin.AdminRoadmapRequest;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.entity.RoadmapTitle;
import umc.product.domain.roadmap.mapper.AdminRoadmapMapper;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.repository.RoadmapSemesterRepository;
import umc.product.domain.roadmap.repository.RoadmapTitleRepository;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.repository.SemesterRepository;

@Service
@RequiredArgsConstructor
public class AdminRoadmapCommandServiceImpl implements AdminRoadmapCommandService {
  private final RoadmapRepository roadmapRepository;
  private final RoadmapSemesterRepository roadmapSemesterRepository;
  private final RoadmapTitleRepository roadmapTitleRepository;
  private final AdminRoadmapMapper adminRoadmapMapper;

  @Override
  public Roadmap createRoadmap(AdminRoadmapRequest request) {
    Roadmap roadmap = adminRoadmapMapper.toRoadmap(request);
    return roadmapRepository.save(roadmap);
  }

  @Override
  public void createRoadmapSemester(Roadmap roadmap, Semester semester) {
    RoadmapSemester roadmapSemester = adminRoadmapMapper.toRoadmapSemester(roadmap, semester);
    roadmapSemesterRepository.save(roadmapSemester);
  }

  @Override
  public void createRoadmapTitles(Roadmap roadmap, List<String> titles) {
    List<RoadmapTitle> roadmapTitles = titles.stream()
        .map(title -> adminRoadmapMapper.toRoadmapTitle(title, roadmap))
        .toList();

    roadmapTitleRepository.saveAll(roadmapTitles);
  }

}
