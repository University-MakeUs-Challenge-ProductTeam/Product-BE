package umc.product.domain.event.adviser.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.converter.EventConverter;
import umc.product.domain.event.dto.response.event.EventPagingResponse;
import umc.product.domain.event.dto.response.event.EventSummaryResponse;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.service.member.EventService;

@Component
@RequiredArgsConstructor
public class EventAdviser {
    private final EventService eventService;
    private final EventConverter eventConverter;

    public EventPagingResponse<EventSummaryResponse> inquiryEvents(int page, int size){

        Page<Event> eventPage = eventService.inquiryEvents(page, size);

        return eventConverter.toEventPagingResponse(eventPage.map(eventConverter::toEventSummaryResponse));
    }
}
