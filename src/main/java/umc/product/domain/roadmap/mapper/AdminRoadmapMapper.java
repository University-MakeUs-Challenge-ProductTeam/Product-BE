package umc.product.domain.roadmap.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.dto.request.admin.AdminRoadmapRequest;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.entity.RoadmapTitle;
import umc.product.domain.semester.entity.Semester;

@Component
public class AdminRoadmapMapper {
  public Roadmap toRoadmap(int week, Part part) {
    return Roadmap.builder()
        .week(week)
        .part(part)
        .build();
  }

  public RoadmapSemester toRoadmapSemester(Roadmap roadmap, Semester semester) {
    return RoadmapSemester.builder()
        .roadmap(roadmap)
        .semester(semester)
        .build();
  }

  public RoadmapTitle toRoadmapTitle(String title, Roadmap roadmap) {
    return RoadmapTitle.builder()
        .title(title)
        .roadmap(roadmap)
        .build();
  }

}
