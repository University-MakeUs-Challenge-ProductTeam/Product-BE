package umc.product.domain.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.university.entity.University;

import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long> {
    Optional<University> findUniversityByName(String name);
}
