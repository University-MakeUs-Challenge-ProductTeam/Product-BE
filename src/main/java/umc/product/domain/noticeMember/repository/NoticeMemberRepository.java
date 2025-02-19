package umc.product.domain.noticeMember.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.noticeMember.entity.NoticeMember;

public interface NoticeMemberRepository extends JpaRepository<NoticeMember, Long> {
}
