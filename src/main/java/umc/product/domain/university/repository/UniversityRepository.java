package umc.product.domain.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.university.entity.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
