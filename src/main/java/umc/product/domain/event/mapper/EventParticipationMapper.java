package umc.product.domain.event.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.request.form.EventFormAnswerRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.entity.form.EventFormQuestion;
import umc.product.domain.event.entity.participation.EventFormAnswer;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.member.entity.Member;

@Component
public class EventParticipationMapper {

    public EventParticipation toParticipationEvent(Member member, Event event, EventForm eventForm) {
        return EventParticipation.builder()
                .participationMember(member)
                .event(event)
                .eventForm(eventForm)
                .build();
    }

    public EventFormAnswer toEventFormAnswer(EventParticipation eventParticipation, EventFormQuestion question, EventFormAnswerRequest request, String filePath) {
        return EventFormAnswer.builder()
                .eventParticipation(eventParticipation)
                .question(question)
                .answerText(request.getAnswerText())
                .filePath(filePath)
                .build();
    }
}
