package umc.product.domain.event.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.repository.EventRepository;
import umc.product.domain.event.service.EventService;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Event getEvent(Long eventId){
        return eventRepository.getEvent(eventId);
    }
}
