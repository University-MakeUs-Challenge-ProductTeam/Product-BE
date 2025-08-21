package umc.product.domain.event.serviceImpl.admin.participation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.event.entity.participation.ParticipationStatus;
import umc.product.domain.event.repository.jpa.participation.EventParticipationRepository;
import umc.product.domain.event.service.member.event.EventService;
import umc.product.domain.event.service.admin.participation.AdminEventParticipationService;

@Service
@RequiredArgsConstructor
public class AdminEventParticipationServiceImpl implements AdminEventParticipationService {

    private final EventParticipationRepository eventParticipationRepository;
    private final EventService eventService;

    @Override
    public int countByEvent(Event event) {
        return eventParticipationRepository.countByEvent(event);
    }

    /*
     * 행사 참여 인원 확인
     */
    @Override
    public Page<EventParticipation> getParticipationEventsByEvent(Long eventId, int page, int size){

        Pageable pageable = PageRequest.of(page, size);
        Event event = eventService.getEvent(eventId);

        return getParticipationEventsByEvent(event, pageable);
    }

    /*
     * 행사 참여 인원 제거
     */
    @Override
    public EventParticipation deleteParticipationEvent(Long participationId){

        EventParticipation eventParticipation = eventParticipationRepository.getEventParticipation(participationId);

        eventParticipationRepository.delete(eventParticipation);

        return eventParticipation;
    }

    /*
     * 출석 상태 변경
     */
    @Override
    public EventParticipation updateParticipationStatus(Long participationId, ParticipationStatus status){

        EventParticipation eventParticipation = eventParticipationRepository.getEventParticipation(participationId);

        eventParticipation.updateParticipationStatus(status);

        return eventParticipation;
    }


    public Page<EventParticipation> getParticipationEventsByEvent(Event event, Pageable pageable){
        return eventParticipationRepository.findAllByEvent(event, pageable);
    }

}
