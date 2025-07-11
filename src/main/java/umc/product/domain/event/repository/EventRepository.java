package umc.product.domain.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
