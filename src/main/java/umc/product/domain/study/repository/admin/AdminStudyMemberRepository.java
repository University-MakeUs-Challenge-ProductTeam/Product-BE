package umc.product.domain.study.repository.admin;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface AdminStudyMemberRepository extends JpaRepository<StudyMember, Long> {

    @Query("SELECT sp.member " +
            "FROM StudyMember sm " +
            "JOIN sm.semesterPart sp " +
            "WHERE sm.study = :study")
    List<Member> findMembersByStudy(@Param("study") Study study);

    boolean existsBySemesterPart_Member_IdInAndSemesterPart_Semester(List<Long> memberIdList, Semester semester);
}
