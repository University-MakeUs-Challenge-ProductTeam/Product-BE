package umc.product.domain.member.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.MemberOut;

import javax.swing.text.html.Option;
import java.util.Optional;


public interface MemberOutJpaRepository extends JpaRepository<MemberOut, Long> {
    Optional<MemberOut> findMemberOutById(Long outId);
}

