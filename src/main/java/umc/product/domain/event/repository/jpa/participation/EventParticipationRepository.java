package umc.product.domain.event.repository.jpa.participation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.global.common.exception.RestApiException;

public interface EventParticipationRepository extends JpaRepository<EventParticipation, Long> {

    default EventParticipation getParticipationEvent(Long participationId) {
        return findById(participationId)
                .orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_FORM_NOT_FOUND));
    }


    int countByEvent(Event event);
    Page<EventParticipation> findAllByEvent(Event event, Pageable pageable);
    EventParticipation findByEvent(Event event);

}
