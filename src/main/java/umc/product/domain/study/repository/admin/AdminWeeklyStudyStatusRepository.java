package umc.product.domain.study.repository.admin;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.entity.WeeklyStudyStatus;

public interface AdminWeeklyStudyStatusRepository extends JpaRepository<WeeklyStudyStatus, Long> {
  Optional<WeeklyStudyStatus> findByStudyMemberAndWeek(StudyMember studyMember, int week);

}
