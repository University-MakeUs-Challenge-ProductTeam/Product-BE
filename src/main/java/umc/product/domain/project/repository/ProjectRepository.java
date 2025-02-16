package umc.product.domain.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectCustomRepository {
}
