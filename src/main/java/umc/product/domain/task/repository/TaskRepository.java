package umc.product.domain.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
