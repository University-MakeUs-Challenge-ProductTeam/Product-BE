package umc.product.domain.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.entity.StudyMember;

import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    Optional<StudyMember> findBySemesterPart_MemberAndStudy_Id(Member member, Long studyId);
}
