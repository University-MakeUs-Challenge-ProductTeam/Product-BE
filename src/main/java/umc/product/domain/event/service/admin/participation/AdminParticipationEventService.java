package umc.product.domain.event.service.admin.participation;

import umc.product.domain.event.entity.event.Event;

public interface AdminParticipationEventService {
    int countByEvent(Event event);
}
