package umc.product.domain.event.repository.jpa.participation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.ParticipationEvent;

public interface ParticipationEventRepository extends JpaRepository<ParticipationEvent, Long> {
    int countByEvent(Event event);
    Page<ParticipationEvent> findAllByEvent(Event event, Pageable pageable);
    ParticipationEvent findByEvent(Event event);

}
