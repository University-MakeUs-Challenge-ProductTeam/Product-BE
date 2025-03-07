package umc.product.domain.study.repository.member;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.entity.StudyMember;

import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    // 페치 조인 사용하지 않고 StudyMember 조회
    Optional<StudyMember> findBySemesterPart_MemberAndStudy_Id(Member member, Long studyId);

    // 페치 조인 사용해서 StudyMember 조회
    @Query("select sm from StudyMember sm " +
            "join fetch sm.semesterPart sp " +
            "join fetch sp.member m " +
            "where m = :member and sm.study.id = :studyId")
    Optional<StudyMember> findBySemesterPart_MemberAndStudy_IdFetch(
            @Param("member") Member member,
            @Param("studyId") Long studyId);
}
