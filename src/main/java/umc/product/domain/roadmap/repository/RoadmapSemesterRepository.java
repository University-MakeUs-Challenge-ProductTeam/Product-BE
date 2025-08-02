package umc.product.domain.roadmap.repository;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;

public interface RoadmapSemesterRepository extends JpaRepository<RoadmapSemester, Long> {

  void deleteAllByRoadmap(Roadmap roadmap);

  @Query("SELECT rs FROM RoadmapSemester rs " +
      "WHERE rs.roadmap = :roadmap AND rs.semester.id = :semesterId")
  Optional<RoadmapSemester> findByRoadmapAndSemesterId(
      @Param("roadmap") Roadmap roadmap,
      @Param("semesterId") Long semesterId
  );
}
