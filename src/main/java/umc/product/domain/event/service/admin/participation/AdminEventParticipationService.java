package umc.product.domain.event.service.admin.participation;

import org.springframework.data.domain.Page;
import umc.product.domain.event.dto.request.participation.ParticipationUpdateRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.EventParticipation;

public interface AdminEventParticipationService {
    int countByEvent(Event event);
    EventParticipation updateParticipationStatus(ParticipationUpdateRequest request);
    EventParticipation deleteParticipationEvent(Long participationId);
    Page<EventParticipation> getParticipationEventsByEvent(Long eventId, int page, int size);
}
