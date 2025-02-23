package umc.product.domain.semester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;

@Repository
public interface SemesterPositionRepository extends JpaRepository<SemesterPosition, Long> {
}
