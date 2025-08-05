package umc.product.domain.event.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.request.form.EventFormQuestionRequest;
import umc.product.domain.event.dto.request.form.EventFormRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.entity.form.EventFormQuestion;

@Component
public class EventFormMapper {

    public EventForm toEventForm(EventFormRequest request, Event event) {
        return EventForm.builder()
                .formTitle(request.getFormTitle())
                .description(request.getDescription())
                .event(event)
                .build();
    }

    public EventFormQuestion toEventFormQuestion(EventFormQuestionRequest request, EventForm eventForm) {
        return EventFormQuestion.builder()
                .questionTitle(request.getQuestionTitle())
                .questionContent(request.getQuestionContent())
                .responseType(request.getResponseType())
                .questionOrder(request.getQuestionOrder())
                .eventForm(eventForm)
                .build();

    }
}
