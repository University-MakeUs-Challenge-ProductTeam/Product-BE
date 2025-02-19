package umc.product.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
