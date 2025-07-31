package umc.product.domain.event.serviceImpl.member.form;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.entity.form.EventFormQuestion;
import umc.product.domain.event.repository.jpa.form.EventFormRepository;
import umc.product.domain.event.service.member.form.EventFormService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventFormServiceImpl implements EventFormService {

    private final EventFormRepository eventFormRepository;

    @Override
    public List<EventFormQuestion> inquiryEventForm(Long eventId){

        EventForm eventForm = eventFormRepository.getEventFormById(eventId);

        return eventForm.getQuestionList();
    }
}
