package umc.product.domain.semester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.semester.entity.Semester;

import java.util.List;
import java.util.Optional;

public interface SemesterJpaRepository extends JpaRepository<Semester, Long> {
    Optional<Semester> findByName(String name);
    List<Semester> findAllByNameIn(List<String> names);
}
