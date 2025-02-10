package umc.product.domain.member.repository;

import org.springframework.data.repository.CrudRepository;
import umc.product.domain.member.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>
{
    Optional<RefreshToken> findByMemberId(Long memberId);
    boolean existsByMemberIdAndRefreshToken(Long memberId, String refreshToken);
}

