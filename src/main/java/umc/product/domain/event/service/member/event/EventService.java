package umc.product.domain.event.service.member.event;

import org.springframework.data.domain.Page;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventReview;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.member.entity.Member;

import java.util.List;

public interface EventService {
    Event getEvent(Long eventId);
    Page<Event> inquiryEvents(int page, int size);
    Page<Event> inquiryEventsByKeyword(String keyword, int page, int size);
    Page<Event> inquiryEventsByEventType(EventType type, int page, int size);
    EventReview createEventReview(Long eventId, Member member, String content);
    EventReview updateEventReview(Member member, Long reviewId, String content);
    Long deleteEventReview(Member member, Long reviewId);
}
