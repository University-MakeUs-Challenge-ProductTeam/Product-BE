package umc.product.domain.member.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.MemberLoginInfo;

import java.util.Optional;

public interface MemberLoginInfoJpaRepository extends JpaRepository<MemberLoginInfo, Long> {
}
