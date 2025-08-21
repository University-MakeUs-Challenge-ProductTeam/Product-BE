package umc.product.domain.event.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.converter.EventConverter;
import umc.product.domain.event.dto.response.event.EventDetailResponse;
import umc.product.domain.event.dto.response.event.EventPagingResponse;
import umc.product.domain.event.dto.response.event.EventSummaryResponse;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.event.service.member.event.EventService;
import umc.product.domain.member.entity.Member;

@Component
@RequiredArgsConstructor
public class EventAdviser {
    private final EventService eventService;
    private final EventConverter eventConverter;

    public EventPagingResponse<EventSummaryResponse> inquiryEvents(int page, int size){

        Page<Event> eventPage = eventService.inquiryEvents(page, size);

        return eventConverter.toEventPagingResponse(eventPage.map(eventConverter::toEventSummaryResponse));
    }

    public EventPagingResponse<EventSummaryResponse> inquiryEventsByEventType(EventType type, int page, int size){

        Page<Event> eventPage = eventService.inquiryEventsByEventType(type, page, size);

        return eventConverter.toEventPagingResponse(eventPage.map(eventConverter::toEventSummaryResponse));
    }

    public EventPagingResponse<EventSummaryResponse> inquiryEventsByKeyword(String keyword, int page, int size){

        Page<Event> eventPage = eventService.inquiryEventsByKeyword(keyword, page, size);

        return eventConverter.toEventPagingResponse(eventPage.map(eventConverter::toEventSummaryResponse));
    }

    public EventDetailResponse inquiryEventDetail(Long eventId){
        Event event = eventService.getEvent(eventId);

        return eventConverter.toEventDetailResponse(event);
    }


}
