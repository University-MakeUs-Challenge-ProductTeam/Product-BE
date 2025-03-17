package umc.product.domain.study.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface AdminStudyMemberRepository extends JpaRepository<StudyMember, Long> {

    boolean existsBySemesterPart_Member_IdInAndSemesterPart_Semester(List<Long> memberIdList, Semester semester);
}
