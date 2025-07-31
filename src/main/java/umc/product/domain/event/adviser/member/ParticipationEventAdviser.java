package umc.product.domain.event.adviser.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.event.converter.EventConverter;
import umc.product.domain.event.converter.ParticipationEventConverter;
import umc.product.domain.event.dto.request.form.EventFormAnswerRequest;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.entity.participation.ParticipationEvent;
import umc.product.domain.event.service.member.participation.EventParticipationService;
import umc.product.domain.member.entity.Member;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipationEventAdviser {

    private final EventParticipationService eventParticipationService;
    private final ParticipationEventConverter participationEventConverter;


    public ParticipationIdResponse applyEvent(Member member, List<EventFormAnswerRequest> requestList){

        ParticipationEvent participationEvent = eventParticipationService.applyEvent(member, requestList);

        return participationEventConverter.toParticipationId(participationEvent);
    }
}
