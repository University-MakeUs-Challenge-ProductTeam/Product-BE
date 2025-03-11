package umc.product.domain.study.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.study.entity.StudyAttendance;

public interface AdminStudyAttendanceRepository extends JpaRepository<StudyAttendance, Long> {
}
