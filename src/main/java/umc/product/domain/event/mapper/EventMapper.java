package umc.product.domain.event.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;

import java.util.List;

@Component
public class EventMapper {

    public Event toEvent(EventRequest request, Semester semester, Member writer, List<Semester> allowedSemesters) {
        return Event.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .eventType(request.getEventType())
                .semester(semester)
                .eventDate(request.getEventDate())
                .eventTime(request.getEventTime())
                .location(request.getLocation())
                .maxParticipants(request.getMaxParticipants())
                .writer(writer)
                .allowedSemesterList(allowedSemesters)
                .allowedPartList(request.getAllowedPartList())
                .allowedRoleList(request.getAllowedRoleList())
                .build();
    }
}
