package umc.product.domain.event.adviser.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.event.converter.EventParticipationConverter;
import umc.product.domain.event.dto.request.form.EventFormAnswerRequest;
import umc.product.domain.event.dto.request.participation.ParticipationCancelRequest;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.event.service.member.participation.EventParticipationService;
import umc.product.domain.member.entity.Member;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventParticipationAdviser {

    private final EventParticipationService eventParticipationService;
    private final EventParticipationConverter eventParticipationConverter;


    public ParticipationIdResponse applyEvent(Member member, List<EventFormAnswerRequest> requestList){

        EventParticipation eventParticipation = eventParticipationService.applyEvent(member, requestList);

        return eventParticipationConverter.toParticipationId(eventParticipation);
    }

    public ParticipationIdResponse cancelParticipation(Member member, ParticipationCancelRequest request){

        EventParticipation eventParticipation = eventParticipationService.cancelParticipation(member, request);

        return eventParticipationConverter.toParticipationId(eventParticipation);
    }
}
