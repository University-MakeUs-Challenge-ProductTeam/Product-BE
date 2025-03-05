package umc.product.domain.checklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.checklist.entity.ChecklistMemberAnswer;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface ChecklistMemberAnswerRepository extends JpaRepository<ChecklistMemberAnswer, Long> {

    @Query("select cma from ChecklistMemberAnswer cma " +
            "join fetch cma.checklistContent cc " +
            "join cc.checklist ch " +
            "join ch.roadmapSemester rs " +
            "join rs.roadmap r " +
            "where cma.studyMember = :studyMember " +
            "and r.week = :week")
    List<ChecklistMemberAnswer> findAllByStudyMemberAndWeek(StudyMember studyMember, int week);
}
