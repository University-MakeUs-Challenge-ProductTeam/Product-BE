package umc.product.domain.member.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.MemberOut;

import javax.swing.text.html.Option;
import java.util.Optional;
import umc.product.domain.study.entity.WeeklyStudyStatus;


public interface MemberOutJpaRepository extends JpaRepository<MemberOut, Long> {
    Optional<MemberOut> findMemberOutById(Long outId);

    Optional<MemberOut> findByWeeklyStudyStatus(WeeklyStudyStatus weeklyStudyStatus);
}

