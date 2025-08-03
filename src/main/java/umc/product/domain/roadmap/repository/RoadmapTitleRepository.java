package umc.product.domain.roadmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapTitle;

public interface RoadmapTitleRepository extends JpaRepository<RoadmapTitle, Long> {
  void deleteAllByRoadmap(Roadmap roadmap);

}
