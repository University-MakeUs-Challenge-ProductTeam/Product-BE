package umc.product.domain.roadmap.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.entity.RoadmapWeek;
import umc.product.domain.semester.entity.Semester;

@Component
public class AdminRoadmapMapper {
  public Roadmap toRoadmap(String title, Part part) {
    return Roadmap.builder()
        .title(title)
        .part(part)
        .build();
  }

  public RoadmapSemester toRoadmapSemester(Roadmap roadmap, Semester semester) {
    return RoadmapSemester.builder()
        .roadmap(roadmap)
        .semester(semester)
        .build();
  }

  public RoadmapWeek toRoadmapTitle(String title, Roadmap roadmap) {
    return RoadmapWeek.builder()
        .title(title)
        .roadmap(roadmap)
        .build();
  }

}
