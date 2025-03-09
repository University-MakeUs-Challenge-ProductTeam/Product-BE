package umc.product.domain.branchUniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.branchUniversity.entity.BranchUniversity;
import umc.product.domain.university.entity.University;

import java.util.Optional;

public interface BranchUniversityRepository extends JpaRepository<BranchUniversity, Long> {

    Optional<BranchUniversity> findByUniversity(University university);;
}
