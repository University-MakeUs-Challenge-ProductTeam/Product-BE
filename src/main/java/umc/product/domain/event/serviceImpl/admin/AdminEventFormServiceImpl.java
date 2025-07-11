package umc.product.domain.event.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.event.dto.request.form.EventFormQuestionRequest;
import umc.product.domain.event.dto.request.form.EventFormRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.entity.form.EventFormQuestion;
import umc.product.domain.event.mapper.EventFormMapper;
import umc.product.domain.event.repository.EventFormQuestionRepository;
import umc.product.domain.event.repository.EventFormRepository;
import umc.product.domain.event.service.admin.AdminEventFormService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEventFormServiceImpl implements AdminEventFormService {

    private final EventFormRepository eventFormRepository;
    private final EventFormQuestionRepository eventFormQuestionRepository;
    private final EventFormMapper eventFormMapper;


    /*
     * 행사 신청 폼 생성
     */
    @Override
    @Transactional
    public EventForm createForm(Event event, EventFormRequest request){

        EventForm newEventForm = createAndSaveEventForm(request, event);

        createAndSaveEventFormQuestions(request.getQuestionList(), newEventForm);

        return newEventForm;
    }

    private EventForm createAndSaveEventForm(EventFormRequest request, Event event) {
        EventForm form = eventFormMapper.toEventForm(request, event);
        return eventFormRepository.save(form);
    }

    private void createAndSaveEventFormQuestions(List<EventFormQuestionRequest> questionRequests, EventForm form) {
        List<EventFormQuestion> questions = questionRequests.stream()
                .map(q -> eventFormMapper.toEventFormQuestion(q, form))
                .toList();

        eventFormQuestionRepository.saveAll(questions);
    }

}
