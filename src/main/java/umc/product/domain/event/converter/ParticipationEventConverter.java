package umc.product.domain.event.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.response.event.EventPagingResponse;
import umc.product.domain.event.dto.response.participation.AdminParticipationMemberResponse;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.dto.response.participation.ParticipationPagingResponse;
import umc.product.domain.event.entity.participation.ParticipationEvent;

@Component
@RequiredArgsConstructor
public class ParticipationEventConverter {

    public ParticipationIdResponse toParticipationId(ParticipationEvent participationEvent) {
        return ParticipationIdResponse.builder()
                .participationId(participationEvent.getId())
                .build();
    }

    public AdminParticipationMemberResponse toAdminParticipationEventResponse(ParticipationEvent participationEvent) {
        return AdminParticipationMemberResponse.builder()
                .participationEventId(participationEvent.getId())
                .nickName(participationEvent.getParticipationMember().getNickName())
                .name(participationEvent.getParticipationMember().getName())
                .profileImage(participationEvent.getParticipationMember().getAvatarUrl())
                .university(participationEvent.getParticipationMember().getUniversity().getName())
                .participationStatus(participationEvent.getParticipationStatus())
                .build();
    }

    public <T> ParticipationPagingResponse<T> toParticipationPagingResponse(Page<T> participations) {
        return ParticipationPagingResponse.<T>builder()
                .participations(participations.getContent())
                .page(participations.getNumber())
                .totalPages(participations.getTotalPages())
                .totalElements((int) participations.getTotalElements())
                .isFirst(participations.isFirst())
                .isLast(participations.isLast())
                .build();
    }
}
