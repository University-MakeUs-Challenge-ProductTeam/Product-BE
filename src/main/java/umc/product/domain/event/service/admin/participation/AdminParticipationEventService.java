package umc.product.domain.event.service.admin.participation;

import org.springframework.data.domain.Page;
import umc.product.domain.event.dto.request.participation.ParticipationUpdateRequest;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.ParticipationEvent;

public interface AdminParticipationEventService {
    int countByEvent(Event event);
    ParticipationEvent updateParticipationStatus(ParticipationUpdateRequest request);
    ParticipationEvent deleteParticipationEvent(Long participationId);
    Page<ParticipationEvent> getParticipationEventsByEvent(Long eventId, int page, int size);
}
