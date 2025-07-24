package umc.product.domain.event.adviser.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.event.converter.EventFormConverter;
import umc.product.domain.event.dto.response.form.EventFormQuestionResponse;
import umc.product.domain.event.dto.response.form.EventFormResponse;
import umc.product.domain.event.entity.form.EventFormQuestion;
import umc.product.domain.event.service.member.form.EventFormService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventFormAdviser {
    private final EventFormService eventFormService;
    private final EventFormConverter eventFormConverter;

    public EventFormResponse inquiryEventForm(Long eventId){

        List<EventFormQuestion> questions = eventFormService.inquiryEventForm(eventId);

        List<EventFormQuestionResponse> questionsResponse = questions.stream()
                .map(eventFormConverter::toEventFormQuestionResponse).toList();

        return eventFormConverter.toEventFormResponse(questions.get(0).getEventForm(), questionsResponse);
    }
}
