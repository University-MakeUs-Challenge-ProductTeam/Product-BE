package umc.product.domain.checklist.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.semester.entity.Semester;

import java.util.List;

public interface ChecklistContentRepository extends JpaRepository<ChecklistContent, Long> {

    @Query("SELECT DISTINCT cc FROM RoadmapSemester rs " +
            "JOIN rs.checklistList c " +
            "JOIN c.checklistContentList cc " +
            "WHERE rs.semester = :semester AND rs.roadmap.part = :part")
    List<ChecklistContent> findChecklistContentsBySemesterAndPart(@Param("semester") Semester semester,
                                                                  @Param("part") Part part);
}
