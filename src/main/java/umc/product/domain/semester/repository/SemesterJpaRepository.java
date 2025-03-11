package umc.product.domain.semester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.semester.entity.Semester;

public interface SemesterJpaRepository extends JpaRepository<Semester, Long> {
}
