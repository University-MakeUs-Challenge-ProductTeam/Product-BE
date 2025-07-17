package umc.product.domain.event.repository.jpa.participation;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.ParticipationEvent;

public interface ParticipationEventRepository extends JpaRepository<ParticipationEvent, Long> {
    int countByEvent(Event event);
}
