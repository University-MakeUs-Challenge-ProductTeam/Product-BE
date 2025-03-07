package umc.product.domain.study.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.study.entity.Study;

public interface StudyRepository extends JpaRepository<Study, Long>, StudyCustomRepository {
}
