package umc.product.domain.roadmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.roadmap.entity.RoadmapTitle;

public interface RoadmapTitleRepository extends JpaRepository<RoadmapTitle, Long> {

}
