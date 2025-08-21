package umc.product.domain.event.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.converter.EventConverter;
import umc.product.domain.event.dto.response.event.*;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventReview;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.event.service.member.event.EventService;
import umc.product.domain.member.entity.Member;

import java.util.List;

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
        List<EventReview> reviews = eventService.inquiryEventReviews(eventId);
        List<EventReviewResponse> reviewResponses = reviews.stream()
                .map(eventConverter::toEventReviewResponse)
                .toList();
        return eventConverter.toEventDetailResponse(event, reviewResponses);
    }

    public EventReviewIdResponse createReview(Long eventId, Member member, String content){

        return new EventReviewIdResponse(eventService.createEventReview(eventId, member, content).getId());
    }

    public EventReviewIdResponse updateReview(Member member, Long reviewId, String content){
        return new EventReviewIdResponse(eventService.updateEventReview(member, reviewId, content).getId());
    }

    public EventReviewIdResponse deleteReview(Member member, Long reviewId){
        return new EventReviewIdResponse(eventService.deleteEventReview(member, reviewId));
    }
}
