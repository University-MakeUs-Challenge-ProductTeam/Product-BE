package umc.product.domain.event.serviceImpl.member.participation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.event.dto.request.form.EventFormAnswerRequest;
import umc.product.domain.event.dto.request.participation.ParticipationCancelRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.entity.form.EventFormQuestion;
import umc.product.domain.event.entity.form.ResponseType;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.event.mapper.EventParticipationMapper;
import umc.product.domain.event.repository.jpa.form.EventFormAnswerRepository;
import umc.product.domain.event.repository.jpa.form.EventFormQuestionRepository;
import umc.product.domain.event.repository.jpa.participation.EventParticipationRepository;
import umc.product.domain.event.service.member.participation.EventParticipationService;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.domain.event.validator.EventParamValidator;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.util.S3FileUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipationServiceImpl implements EventParticipationService {

    private final EventParticipationMapper eventParticipationMapper;
    private final EventFormQuestionRepository eventFormQuestionRepository;
    private final EventParticipationRepository eventParticipationRepository;
    private final EventFormAnswerRepository eventFormAnswerRepository;
    private final S3FileUtil s3FileUtil;

    /*
     * 행사 신청
     */
    @Override
    @Transactional
    public EventParticipation applyEvent(Member member, List<EventFormAnswerRequest> requestList){

        if (requestList == null || requestList.isEmpty()) {
            throw new RestApiException(EventErrorStatus.EMPTY_ANSWER);
        }

        EventFormQuestion firstQuestion = eventFormQuestionRepository.getEventFormQuestion(requestList.get(0).getQuestionId());
        EventForm eventForm = firstQuestion.getEventForm();
        Event event = eventForm.getEvent();

        EventParticipation eventParticipation = eventParticipationMapper.toParticipationEvent(member, event, eventForm);
        eventParticipationRepository.save(eventParticipation);

        for (EventFormAnswerRequest request : requestList) {
            EventFormQuestion question = eventFormQuestionRepository.getEventFormQuestion(request.getQuestionId());

            String uploadedFilePath = null;
            if (request.getResponseType() == ResponseType.FILE_UPLOAD) {
                uploadedFilePath = s3FileUtil.uploadFile("answer", request.getAnswerFile());
            }

            EventFormAnswer answer = eventParticipationMapper.toEventFormAnswer(
                    eventParticipation,
                    question,
                    request,
                    uploadedFilePath
            );
            eventFormAnswerRepository.save(answer);
        }

        return eventParticipation;
    }

    /*
     * 행사 취소
     */
    @Override
    @Transactional
    public EventParticipation cancelParticipation(Member member, ParticipationCancelRequest request){
        EventParticipation participation = eventParticipationRepository.getEventParticipation(request.getParticipationId());

        //행사 신청 본인 확인 유효성 검사
        EventParamValidator.validModify(member.getId(), participation.getParticipationMember().getId());

        CancelReason cancelReason = eventParticipationMapper.toCancelReason(request);

        participation.cancel(cancelReason);

        return participation;
    }

}
