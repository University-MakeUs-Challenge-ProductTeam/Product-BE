package umc.product.domain.study.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.study.entity.StudyAttendance;
import umc.product.domain.study.entity.StudyMember;

import java.util.Optional;

public interface StudyAttendanceRepository extends JpaRepository<StudyAttendance, Long> {

    Optional<StudyAttendance> findByStudyMemberAndWeek(StudyMember studyMember, int week);
}
