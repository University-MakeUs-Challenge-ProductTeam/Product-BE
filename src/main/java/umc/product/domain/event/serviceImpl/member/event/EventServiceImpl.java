package umc.product.domain.event.serviceImpl.member.event;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventReview;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.event.mapper.EventReviewMapper;
import umc.product.domain.event.repository.jpa.event.EventRepository;
import umc.product.domain.event.repository.jpa.event.EventReviewRepository;
import umc.product.domain.event.service.member.event.EventService;
import umc.product.domain.member.entity.Member;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventReviewMapper eventReviewMapper;
    private final EventReviewRepository eventReviewRepository;

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
    public Page<Event> inquiryEventsByEventType(EventType type, int page, int size){

        Pageable pageable = PageRequest.of(page, size);

        return eventRepository.findAllByEventType(type, pageable);
    }

    @Override
    public Page<Event> inquiryEventsByKeyword(String keyword, int page, int size){

        Pageable pageable = PageRequest.of(page, size);

        return eventRepository.searchByKeyword(keyword, pageable);
    }

    @Override
    @Transactional
    public EventReview createEventReview(Long eventId, Member member, String content){

        Event event = eventRepository.getEvent(eventId);
        EventReview newEventReview = eventReviewMapper.toEventReview(event, member, content);
        return eventReviewRepository.save(newEventReview);
    }


}
