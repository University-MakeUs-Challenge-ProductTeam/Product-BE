package umc.product.domain.event.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;

@Component
public class EventMapper {

    public Event toEvent(EventRequest request, Semester semester, Member writer) {
        return Event.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .eventType(request.getEventType())
                .semester(semester)
                .eventStartDate(request.getEventStartDate())
                .eventEndDate(request.getEventEndDate())
                .location(request.getLocation())
                .maxParticipants(request.getMaxParticipants())
                .writer(writer)
                .build();
    }
}
