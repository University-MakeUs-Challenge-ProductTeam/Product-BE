package umc.product.domain.event.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.response.participation.AdminParticipationMemberResponse;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.dto.response.participation.ParticipationPagingResponse;
import umc.product.domain.event.entity.participation.EventParticipation;

@Component
@RequiredArgsConstructor
public class EventParticipationConverter {

    public ParticipationIdResponse toParticipationId(EventParticipation eventParticipation) {
        return ParticipationIdResponse.builder()
                .participationId(eventParticipation.getId())
                .build();
    }

    public AdminParticipationMemberResponse toAdminParticipationEventResponse(EventParticipation eventParticipation) {
        return AdminParticipationMemberResponse.builder()
                .participationEventId(eventParticipation.getId())
                .nickName(eventParticipation.getParticipationMember().getNickName())
                .name(eventParticipation.getParticipationMember().getName())
                .profileImage(eventParticipation.getParticipationMember().getAvatarUrl())
                .university(eventParticipation.getParticipationMember().getUniversity().getName())
                .participationStatus(eventParticipation.getParticipationStatus())
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
