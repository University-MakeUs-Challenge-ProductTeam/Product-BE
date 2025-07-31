package umc.product.domain.event.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.request.form.EventFormAnswerRequest;
import umc.product.domain.event.dto.request.form.EventFormRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.entity.form.EventFormQuestion;
import umc.product.domain.event.entity.participation.EventFormAnswer;
import umc.product.domain.event.entity.participation.ParticipationEvent;
import umc.product.domain.member.entity.Member;

import java.util.List;

@Component
public class ParticipationEventMapper {

    public ParticipationEvent toParticipationEvent(Member member, Event event, EventForm eventForm) {
        return ParticipationEvent.builder()
                .participationMember(member)
                .event(event)
                .eventForm(eventForm)
                .build();
    }

    public EventFormAnswer toEventFormAnswer(ParticipationEvent participationEvent, EventFormQuestion question, EventFormAnswerRequest request, String filePath) {
        return EventFormAnswer.builder()
                .participationEvent(participationEvent)
                .question(question)
                .answerText(request.getAnswerText())
                .filePath(filePath)
                .build();
    }
}
