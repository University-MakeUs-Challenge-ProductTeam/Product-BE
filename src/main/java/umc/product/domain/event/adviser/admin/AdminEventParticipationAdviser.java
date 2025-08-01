package umc.product.domain.event.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.converter.EventParticipationConverter;
import umc.product.domain.event.dto.request.participation.ParticipationUpdateRequest;
import umc.product.domain.event.dto.response.participation.AdminParticipationMemberResponse;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.dto.response.participation.ParticipationPagingResponse;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.event.service.admin.participation.AdminEventParticipationService;

@Component
@RequiredArgsConstructor
public class AdminEventParticipationAdviser {

    private final AdminEventParticipationService adminEventParticipationService;
    private final EventParticipationConverter eventParticipationConverter;

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
