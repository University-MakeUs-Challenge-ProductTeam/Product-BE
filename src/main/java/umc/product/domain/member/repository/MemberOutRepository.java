package umc.product.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.MemberOut;

import java.util.Optional;


public interface MemberOutRepository extends JpaRepository<MemberOut, Long> {
    Optional<MemberOut> findMemberOutById(Long outId);
}

