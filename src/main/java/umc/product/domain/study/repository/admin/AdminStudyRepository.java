package umc.product.domain.study.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.study.entity.Study;

public interface AdminStudyRepository extends JpaRepository<Study, Long> {
}
