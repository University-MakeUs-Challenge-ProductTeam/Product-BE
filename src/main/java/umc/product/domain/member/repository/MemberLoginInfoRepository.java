package umc.product.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.MemberLoginInfo;

import java.util.Optional;

public interface MemberLoginInfoRepository extends JpaRepository<MemberLoginInfo, Long> {
    Optional<MemberLoginInfo> findByMemberLoginId(String memberLoginId);
}
