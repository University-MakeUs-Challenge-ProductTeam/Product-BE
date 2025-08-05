package umc.product.domain.event.service.admin.participation;

import org.springframework.data.domain.Page;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.event.entity.participation.ParticipationStatus;

public interface AdminEventParticipationService {
    int countByEvent(Event event);
    EventParticipation updateParticipationStatus(Long participationId, ParticipationStatus status);
    EventParticipation deleteParticipationEvent(Long participationId);
    Page<EventParticipation> getParticipationEventsByEvent(Long eventId, int page, int size);
}
