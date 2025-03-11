package umc.product.domain.study.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.study.entity.StudyMember;

public interface AdminStudyMemberRepository extends JpaRepository<StudyMember, Long> {
}
