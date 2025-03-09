package umc.product.domain.semester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;

import java.util.List;

public interface SemesterPartJpaRepository extends JpaRepository<SemesterPart, Long> {

    List<SemesterPart> findBySemesterAndPartAndMemberIn(Semester semester, Part part, List<Member> members);
}
