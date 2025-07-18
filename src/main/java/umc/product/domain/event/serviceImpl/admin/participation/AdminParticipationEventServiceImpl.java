package umc.product.domain.event.serviceImpl.admin.participation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.ParticipationEvent;
import umc.product.domain.event.repository.jpa.event.EventRepository;
import umc.product.domain.event.repository.jpa.participation.ParticipationEventRepository;
import umc.product.domain.event.service.EventService;
import umc.product.domain.event.service.admin.participation.AdminParticipationEventService;

@Service
@RequiredArgsConstructor
public class AdminParticipationEventServiceImpl implements AdminParticipationEventService {

    private final ParticipationEventRepository participationEventRepository;
    private final EventService eventService;

    @Override
    public int countByEvent(Event event) {
        return participationEventRepository.countByEvent(event);
    }

    /*
     * 행사 참여 인원 확인
     */
    @Override
    public Page<ParticipationEvent> getParticipationEventsByEvent(Long eventId, int page, int size){

        Pageable pageable = PageRequest.of(page, size);
        Event event = eventService.getEvent(eventId);

        return getParticipationEventsByEvent(event, pageable);
    }

    public ParticipationEvent getParticipationEventByEvent(Event event){
        return participationEventRepository.findByEvent(event);
    }

    public Page<ParticipationEvent> getParticipationEventsByEvent(Event event, Pageable pageable){
        return participationEventRepository.findAllByEvent(event, pageable);
    }

}
