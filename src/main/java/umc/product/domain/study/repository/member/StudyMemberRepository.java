package umc.product.domain.study.repository.member;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    // 페치 조인 사용하지 않고 StudyMember 조회
    Optional<StudyMember> findBySemesterPart_MemberAndStudy_Id(Member member, Long studyId);

    @Query("SELECT sp.member " +
            "FROM StudyMember sm " +
            "JOIN sm.semesterPart sp " +
            "WHERE sm.study = :study")
    List<Member> findMembersByStudy(@Param("study") Study study);
}
