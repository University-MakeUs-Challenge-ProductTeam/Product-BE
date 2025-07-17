package umc.product.domain.event.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.converter.EventConverter;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.dto.request.event.EventUpdateRequest;
import umc.product.domain.event.dto.response.AdminEventSummaryResponse;
import umc.product.domain.event.dto.response.EventIdResponse;
import umc.product.domain.event.dto.response.EventPagingResponse;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.event.service.admin.AdminEventFormService;
import umc.product.domain.event.service.admin.AdminEventService;
import umc.product.domain.member.entity.Member;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminEventAdviser {

    private final AdminEventService adminEventService;
    private final EventConverter eventConverter;

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


}
