package umc.product.domain.roadmap.repository;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.roadmap.entity.RoadmapWeek;

public interface RoadmapWeekRepository extends JpaRepository<RoadmapWeek, Long> {

  @Query("SELECT rw FROM RoadmapWeek rw WHERE rw.roadmap.id = :roadmapId AND rw.week = :week")
  List<RoadmapWeek> findAllByRoadmapIdAndWeek(@Param("roadmapId") Long roadmapId, @Param("week") int week);

  void deleteAllByRoadmapId(Long roadmapId);

}
