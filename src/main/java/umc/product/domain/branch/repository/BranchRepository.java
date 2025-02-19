package umc.product.domain.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.branch.entity.Branch;
import umc.product.domain.branchUniversity.entity.BranchUniversity;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
