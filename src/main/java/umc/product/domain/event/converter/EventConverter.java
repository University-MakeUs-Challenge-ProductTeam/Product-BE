package umc.product.domain.event.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.response.AdminEventDetailResponse;
import umc.product.domain.event.dto.response.AdminEventSummaryResponse;
import umc.product.domain.event.dto.response.EventIdResponse;
import umc.product.domain.event.dto.response.EventPagingResponse;
import umc.product.domain.event.entity.event.Event;

@Component
@RequiredArgsConstructor
public class EventConverter {

    public EventIdResponse toEventIdResponse(Event event) {
        return EventIdResponse.builder()
                .eventId(event.getId())
                .build();
    }

    public <T> EventPagingResponse<T> toEventPagingResponse(Page<T> events) {
        return EventPagingResponse.<T>builder()
                .events(events.getContent())
                .page(events.getNumber())
                .totalPages(events.getTotalPages())
                .totalElements((int) events.getTotalElements())
                .isFirst(events.isFirst())
                .isLast(events.isLast())
                .build();
    }

    public AdminEventSummaryResponse toAdminEventSummaryResponse(Event event, Integer connectedNotices, Integer participants) {
        return AdminEventSummaryResponse.builder()
                .eventId(event.getId())
                .eventStartDate(event.getEventStartDate())
                .eventEndDate(event.getEventEndDate())
                .location(event.getLocation())
                .thumbnail(event.getThumbnail())
                .connectedNotices(connectedNotices)
                .eventType(event.getEventType())
                .participants(participants)
                .maxParticipants(event.getMaxParticipants())
                .build();
    }

    public AdminEventDetailResponse toAdminEventDetailResponse(Event event, Integer participants) {
        return AdminEventDetailResponse.builder()
                .eventId(event.getId())
                .content(event.getContent())
                .eventStartDate(event.getEventStartDate())
                .eventEndDate(event.getEventEndDate())
                .location(event.getLocation())
                .thumbnail(event.getThumbnail())
                .eventType(event.getEventType())
                .participants(participants)
                .registrationStartDate(event.getRegistrationSettings().getRegistrationStartDate())
                .registrationEndDate(event.getRegistrationSettings().getRegistrationEndDate())
                .cancellationDeadline(event.getRegistrationSettings().getCancellationDeadline())
                .maxParticipants(event.getMaxParticipants())
                .build();
    }
}
