package umc.product.domain.semester.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterCurrent;

import java.util.Optional;

public interface SemesterCurrentRepository extends JpaRepository<SemesterCurrent, Long> {

    // 현재 기수 조회
    @Query("SELECT sc.semester FROM SemesterCurrent sc")
    Optional<Semester> findCurrentSemester();
}
