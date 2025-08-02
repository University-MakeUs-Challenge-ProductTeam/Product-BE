package umc.product.domain.checklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.roadmap.entity.Roadmap;


public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

}
