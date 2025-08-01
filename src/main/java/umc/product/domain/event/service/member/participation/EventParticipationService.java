package umc.product.domain.event.service.member.participation;

import umc.product.domain.event.dto.request.form.EventFormAnswerRequest;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.member.entity.Member;

import java.util.List;

public interface EventParticipationService {
    EventParticipation applyEvent(Member member, List<EventFormAnswerRequest> requestList);
}
