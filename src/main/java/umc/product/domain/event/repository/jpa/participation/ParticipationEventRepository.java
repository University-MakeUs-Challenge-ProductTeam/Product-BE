package umc.product.domain.event.repository.jpa.participation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.ParticipationEvent;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.global.common.exception.RestApiException;

public interface ParticipationEventRepository extends JpaRepository<ParticipationEvent, Long> {

    default ParticipationEvent getParticipationEvent(Long participationId) {
        return findById(participationId)
                .orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_NOT_FOUND));
    }


    int countByEvent(Event event);
    Page<ParticipationEvent> findAllByEvent(Event event, Pageable pageable);
    ParticipationEvent findByEvent(Event event);

}
