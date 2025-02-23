package umc.product.domain.semester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
}
