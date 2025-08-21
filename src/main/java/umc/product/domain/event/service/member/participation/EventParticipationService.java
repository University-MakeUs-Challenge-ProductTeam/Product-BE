package umc.product.domain.event.service.member.participation;

import umc.product.domain.event.dto.request.participation.ParticipationCancelRequest;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.member.entity.Member;

import java.util.List;

public interface EventParticipationService {
    EventParticipation cancelParticipation(Member member, ParticipationCancelRequest request);
}
