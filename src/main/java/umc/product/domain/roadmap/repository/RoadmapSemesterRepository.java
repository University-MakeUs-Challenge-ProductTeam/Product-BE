package umc.product.domain.roadmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;

public interface RoadmapSemesterRepository extends JpaRepository<RoadmapSemester, Long> {

  void deleteAllByRoadmap(Roadmap roadmap);
}
