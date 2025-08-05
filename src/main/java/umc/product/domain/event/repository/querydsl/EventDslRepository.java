package umc.product.domain.event.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventType;

public interface EventDslRepository {
    Page<Event> findByFilter(Pageable pageable, Integer month, String semester, EventType eventType);
}
