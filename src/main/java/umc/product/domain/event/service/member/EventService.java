package umc.product.domain.event.service.member;

import org.springframework.data.domain.Page;
import umc.product.domain.event.dto.response.event.EventSummaryResponse;
import umc.product.domain.event.entity.event.Event;

public interface EventService {
    Event getEvent(Long eventId);
    Page<Event> inquiryEvents(int page, int size);
    Page<Event> inquiryEventsByKeyword(String keyword, int page, int size);
}
