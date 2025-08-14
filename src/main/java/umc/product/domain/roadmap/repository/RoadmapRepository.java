package umc.product.domain.roadmap.repository;

import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.entity.Roadmap;

import java.util.List;
import umc.product.domain.semester.entity.Semester;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {

    @Query("SELECT r FROM Roadmap r " +
            "JOIN r.roadmapSemesterList rs " +
            "WHERE rs.semester.id = :semesterId " +
            "AND r.part = :part " +
            "ORDER BY r.week ASC")
    List<Roadmap> findAllBySemesterIdAndPart(@Param("semesterId") Long semesterId, @Param("part") Part part);


  @Query("SELECT r FROM Roadmap r JOIN r.roadmapSemesterList rs WHERE rs.semester.id = :semesterId AND r.part = :part")
  Optional<Roadmap> findBySemesterIdAndPart(@Param("semesterId") Long semesterId, @Param("part") Part part);
}

