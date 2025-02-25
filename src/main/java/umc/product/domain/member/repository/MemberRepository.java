package umc.product.domain.member.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByClientIdAndLoginType(String clientId, LoginType loginType);
    Optional<Member> findByName(String name);

    boolean existsMemberByClientId(String ClientId);
}

