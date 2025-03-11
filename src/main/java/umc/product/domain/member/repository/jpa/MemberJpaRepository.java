package umc.product.domain.member.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberOut;

import java.util.Optional;


public interface MemberJpaRepository extends JpaRepository<Member, Long> {

}

