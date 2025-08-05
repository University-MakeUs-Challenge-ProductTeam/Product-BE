package umc.product.domain.event.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.converter.EventConverter;
import umc.product.domain.event.converter.EventParticipationConverter;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.dto.request.event.EventUpdateRequest;
import umc.product.domain.event.dto.request.participation.ParticipationUpdateRequest;
import umc.product.domain.event.dto.response.event.AdminEventDetailResponse;
import umc.product.domain.event.dto.response.event.AdminEventSummaryResponse;
import umc.product.domain.event.dto.response.event.EventIdResponse;
import umc.product.domain.event.dto.response.event.EventPagingResponse;
import umc.product.domain.event.dto.response.participation.AdminParticipationMemberResponse;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.dto.response.participation.ParticipationPagingResponse;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.event.service.admin.event.AdminEventService;
import umc.product.domain.event.service.admin.participation.AdminEventParticipationService;
import umc.product.domain.member.entity.Member;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminEventAdviser {

    private final AdminEventService adminEventService;
    private final EventConverter eventConverter;
    private final AdminEventParticipationService adminEventParticipationService;
    private final EventParticipationConverter eventParticipationConverter;

    public EventIdResponse createEvent(Member writer, List<MultipartFile> eventImages, EventRequest request) {

        Event event = adminEventService.createEvent(writer, eventImages, request);

        return eventConverter.toEventIdResponse(event);
    }

    public EventIdResponse updateEvent(
            Member writer, Long eventId, List<MultipartFile> eventImages, EventUpdateRequest request
    ){

        Event event = adminEventService.updateEvent(writer, eventId, request, eventImages);

        return eventConverter.toEventIdResponse(event);
    }

    public EventIdResponse deleteEvent(Member writer, Long eventId){

        Event event = adminEventService.deleteEvent(writer, eventId);

        return eventConverter.toEventIdResponse(event);
    }

    public EventPagingResponse<AdminEventSummaryResponse> inquiryEventsByFilter(
            Integer month, String semester, EventType eventType, int page, int size
    ) {
        Page<AdminEventSummaryResponse> eventPage = adminEventService.inquiryEventsByFilter(month, semester, eventType, page, size);

        return eventConverter.toEventPagingResponse(eventPage);
    }

    public AdminEventDetailResponse inquiryEventDetail(Long eventId){

        Event event = adminEventService.inquiryEventDetail(eventId);
        int participantCount = adminEventParticipationService.countByEvent(event);

        return eventConverter.toAdminEventDetailResponse(event, participantCount);
    }

    public ParticipationPagingResponse<AdminParticipationMemberResponse> inquiryParticipationMembers(Long eventId, int page, int size) {

        Page<EventParticipation> participationEvents = adminEventParticipationService.getParticipationEventsByEvent(eventId, page, size);
        Page<AdminParticipationMemberResponse> participationMemberResponsePage
                = participationEvents.map(eventParticipationConverter::toAdminParticipationEventResponse);
        return eventParticipationConverter.toParticipationPagingResponse(participationMemberResponsePage);
    }

    public ParticipationIdResponse deleteParticipationEvent(Long participationId) {
        EventParticipation eventParticipation = adminEventParticipationService.deleteParticipationEvent(participationId);

        return eventParticipationConverter.toParticipationId(eventParticipation);
    }

    public ParticipationIdResponse updateParticipationStatus(ParticipationUpdateRequest request){

        EventParticipation eventParticipation = adminEventParticipationService.updateParticipationStatus(request);

        return eventParticipationConverter.toParticipationId(eventParticipation);
    }

}
