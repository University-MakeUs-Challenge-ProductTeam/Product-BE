package umc.product.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.enums.NoticeTarget;
import umc.product.domain.notice.repository.querydsl.NoticeDslRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeDslRepository {
    List<Notice> findByTargetOrderByNoticeDateDesc(NoticeTarget target);
    List<Notice> findAllByOrderByNoticeDateDesc();
}
