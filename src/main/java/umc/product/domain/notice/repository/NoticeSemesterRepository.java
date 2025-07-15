package umc.product.domain.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.notice.entity.NoticeSemester;

public interface NoticeSemesterRepository extends JpaRepository<NoticeSemester, Long> {
}
