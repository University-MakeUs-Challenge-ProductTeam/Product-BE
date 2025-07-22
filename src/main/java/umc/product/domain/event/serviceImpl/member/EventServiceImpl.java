package umc.product.domain.event.serviceImpl.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.repository.jpa.event.EventRepository;
import umc.product.domain.event.service.member.EventService;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public Event getEvent(Long eventId){
        return eventRepository.getEvent(eventId);
    }

    @Override
    public Page<Event> inquiryEvents(int page, int size){

        Pageable pageable = PageRequest.of(page, size);

        return eventRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public Page<Event> inquiryEventsByKeyword(String keyword, int page, int size){

        Pageable pageable = PageRequest.of(page, size);

        return eventRepository.searchByKeyword(keyword, pageable);
    }
}
