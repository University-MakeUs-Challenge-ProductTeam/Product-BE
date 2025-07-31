package umc.product.domain.event.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.converter.ParticipationEventConverter;
import umc.product.domain.event.dto.request.participation.ParticipationUpdateRequest;
import umc.product.domain.event.dto.response.participation.AdminParticipationMemberResponse;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.dto.response.participation.ParticipationPagingResponse;
import umc.product.domain.event.entity.participation.ParticipationEvent;
import umc.product.domain.event.service.admin.participation.AdminParticipationEventService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminParticipationEventAdviser {

    private final AdminParticipationEventService adminParticipationEventService;
    private final ParticipationEventConverter participationEventConverter;

    public ParticipationPagingResponse<AdminParticipationMemberResponse> inquiryParticipationMembers(Long eventId, int page, int size) {

        Page<ParticipationEvent> participationEvents = adminParticipationEventService.getParticipationEventsByEvent(eventId, page, size);
        Page<AdminParticipationMemberResponse> participationMemberResponsePage
                = participationEvents.map(participationEventConverter::toAdminParticipationEventResponse);
        return participationEventConverter.toParticipationPagingResponse(participationMemberResponsePage);
    }

    public ParticipationIdResponse deleteParticipationEvent(Long participationId) {
        ParticipationEvent participationEvent = adminParticipationEventService.deleteParticipationEvent(participationId);

        return participationEventConverter.toParticipationId(participationEvent);
    }

    public ParticipationIdResponse updateParticipationStatus(ParticipationUpdateRequest request){

        ParticipationEvent participationEvent = adminParticipationEventService.updateParticipationStatus(request);

        return participationEventConverter.toParticipationId(participationEvent);
    }
}
