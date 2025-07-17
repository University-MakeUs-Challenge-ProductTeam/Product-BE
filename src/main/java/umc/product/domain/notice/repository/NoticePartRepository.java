package umc.product.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.notice.entity.NoticePart;

public interface NoticePartRepository extends JpaRepository<NoticePart, Long> {
}
