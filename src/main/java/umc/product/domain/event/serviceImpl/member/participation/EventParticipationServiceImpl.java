package umc.product.domain.event.serviceImpl.member.participation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.dto.request.form.EventFormAnswerRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.entity.form.EventFormQuestion;
import umc.product.domain.event.entity.form.ResponseType;
import umc.product.domain.event.entity.participation.EventFormAnswer;
import umc.product.domain.event.entity.participation.ParticipationEvent;
import umc.product.domain.event.mapper.ParticipationEventMapper;
import umc.product.domain.event.repository.jpa.form.EventFormAnswerRepository;
import umc.product.domain.event.repository.jpa.form.EventFormQuestionRepository;
import umc.product.domain.event.repository.jpa.participation.ParticipationEventRepository;
import umc.product.domain.event.service.member.participation.EventParticipationService;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.util.S3FileUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipationServiceImpl implements EventParticipationService {

    private final ParticipationEventMapper participationEventMapper;
    private final EventFormQuestionRepository eventFormQuestionRepository;
    private final ParticipationEventRepository participationEventRepository;
    private final EventFormAnswerRepository eventFormAnswerRepository;
    private final S3FileUtil s3FileUtil;

    /*
     * 행사 신청
     */
    @Override
    @Transactional
    public ParticipationEvent applyEvent(Member member, List<EventFormAnswerRequest> requestList){

        if (requestList == null || requestList.isEmpty()) {
            throw new RestApiException(EventErrorStatus.EMPTY_ANSWER);
        }

        EventFormQuestion firstQuestion = eventFormQuestionRepository.getEventFormQuestion(requestList.get(0).getQuestionId());
        EventForm eventForm = firstQuestion.getEventForm();
        Event event = eventForm.getEvent();

        ParticipationEvent participationEvent = participationEventMapper.toParticipationEvent(member, event, eventForm);
        participationEventRepository.save(participationEvent);

        for (EventFormAnswerRequest request : requestList) {
            EventFormQuestion question = eventFormQuestionRepository.getEventFormQuestion(request.getQuestionId());

            String uploadedFilePath = null;
            if (request.getResponseType() == ResponseType.FILE_UPLOAD) {
                uploadedFilePath = s3FileUtil.uploadFile("answer", request.getAnswerFile());
            }

            EventFormAnswer answer = participationEventMapper.toEventFormAnswer(
                    participationEvent,
                    question,
                    request,
                    uploadedFilePath
            );
            eventFormAnswerRepository.save(answer);
        }

        return participationEvent;
    }
}
