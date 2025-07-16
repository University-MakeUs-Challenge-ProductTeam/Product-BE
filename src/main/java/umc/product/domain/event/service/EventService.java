package umc.product.domain.event.service;

import umc.product.domain.event.entity.event.Event;

public interface EventService {
    Event getEvent(Long eventId);
}
