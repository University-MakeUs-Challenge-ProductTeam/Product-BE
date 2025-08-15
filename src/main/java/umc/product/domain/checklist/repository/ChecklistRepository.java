package umc.product.domain.checklist.repository;

import com.querydsl.core.Fetchable;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;


public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

  List<Checklist> findAllByRoadmapSemester(RoadmapSemester roadmapSemester);

  @Query("SELECT c.id FROM Checklist c JOIN c.roadmapSemester rs JOIN rs.roadmap r WHERE r.id = :roadmapId AND c.week = :week")
  Set<Long> findAllIdsByRoadmapIdAndWeek(@Param("roadmapId") Long roadmapId, int week);

  @Query("SELECT ch FROM Checklist ch JOIN FETCH ch.checklistContentList WHERE ch.roadmapSemester = :roadmapSemester")
  List<Checklist> findAllByRoadmapSemesterFetch(@Param("roadmapSemester") RoadmapSemester roadmapSemester);

  @Query("SELECT ch FROM Checklist ch " +
      "LEFT JOIN FETCH ch.checklistContentList " + // checklistContentList를 fetch join
      "WHERE ch.roadmapSemester = :roadmapSemester AND ch.week = :week")
  List<Checklist> findAllByRoadmapSemesterAndWeekFetch(@Param("roadmapSemester") RoadmapSemester roadmapSemester, @Param("week") int week);
}
