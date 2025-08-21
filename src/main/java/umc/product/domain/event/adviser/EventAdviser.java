package umc.product.domain.event.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.converter.EventConverter;
import umc.product.domain.event.converter.EventParticipationConverter;
import umc.product.domain.event.dto.request.participation.ParticipationCancelRequest;
import umc.product.domain.event.dto.response.event.EventPagingResponse;
import umc.product.domain.event.dto.response.event.EventSummaryResponse;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.event.service.member.event.EventService;
import umc.product.domain.event.service.member.participation.EventParticipationService;
import umc.product.domain.member.entity.Member;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventAdviser {
    private final EventService eventService;
    private final EventConverter eventConverter;
    private final EventParticipationService eventParticipationService;
    private final EventParticipationConverter eventParticipationConverter;

    public EventPagingResponse<EventSummaryResponse> inquiryEvents(int page, int size){

        Page<Event> eventPage = eventService.inquiryEvents(page, size);

        return eventConverter.toEventPagingResponse(eventPage.map(eventConverter::toEventSummaryResponse));
    }

    public EventPagingResponse<EventSummaryResponse> inquiryEventsByKeyword(String keyword, int page, int size){

        Page<Event> eventPage = eventService.inquiryEventsByKeyword(keyword, page, size);

        return eventConverter.toEventPagingResponse(eventPage.map(eventConverter::toEventSummaryResponse));
    }

    public EventFormResponse inquiryEventForm(Long eventId){

        List<EventFormQuestion> questions = eventFormService.inquiryEventForm(eventId);

        List<EventFormQuestionResponse> questionsResponse = questions.stream()
                .map(eventFormConverter::toEventFormQuestionResponse).toList();

        return eventFormConverter.toEventFormResponse(questions.get(0).getEventForm(), questionsResponse);
    }

    public ParticipationIdResponse applyEvent(Member member, List<EventFormAnswerRequest> requestList){

        EventParticipation eventParticipation = eventParticipationService.applyEvent(member, requestList);

        return eventParticipationConverter.toParticipationId(eventParticipation);
    }

    public ParticipationIdResponse cancelParticipation(Member member, ParticipationCancelRequest request){

        EventParticipation eventParticipation = eventParticipationService.cancelParticipation(member, request);

        return eventParticipationConverter.toParticipationId(eventParticipation);
    }
}
