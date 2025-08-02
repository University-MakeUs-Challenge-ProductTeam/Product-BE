package umc.product.domain.roadmap.service.admin;

import java.util.List;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.dto.request.admin.AdminRoadmapRequest;
import umc.product.domain.roadmap.dto.response.admin.RoadmapCommonResponse;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.semester.entity.Semester;

public interface AdminRoadmapCommandService {
  Roadmap createRoadmap(Long semesterId, Part part, int week);
  void createRoadmapSemester(Roadmap roadmap, Semester semester);
  void createRoadmapTitles(Roadmap roadmap, List<String> titles);

  void deleteRoadmapsBySemesterAndPart(Semester semester, Part part);
}
