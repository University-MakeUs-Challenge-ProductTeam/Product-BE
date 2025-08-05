package umc.product.domain.event.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.response.form.EventFormQuestionResponse;
import umc.product.domain.event.dto.response.form.EventFormResponse;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.entity.form.EventFormQuestion;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventFormConverter {

    public EventFormResponse toEventFormResponse(EventForm eventForm, List<EventFormQuestionResponse> questions) {
        return EventFormResponse.builder()
                .eventFormId(eventForm.getId())
                .formTitle(eventForm.getFormTitle())
                .description(eventForm.getDescription())
                .questions(questions)
                .build();
    }

    public EventFormQuestionResponse toEventFormQuestionResponse(EventFormQuestion questions) {
        return EventFormQuestionResponse.builder()
                .eventFormQuestionId(questions.getId())
                .questionTitle(questions.getQuestionTitle())
                .questionContent(questions.getQuestionContent())
                .responseType(questions.getResponseType())
                .questionOrder(questions.getQuestionOrder())
                .build();
    }
}
