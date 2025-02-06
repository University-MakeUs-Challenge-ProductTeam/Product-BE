package umc.product.domain.member.repository;

import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByClientIdAndLoginType(String clientId, LoginType loginType);
    Optional<Member> findByName(String name);
}

