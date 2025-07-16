package umc.product.domain.event.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.request.event.EventRegistrationSettingsRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventRegistrationSettings;
import umc.product.domain.semester.entity.Semester;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventRegistrationSettingsMapper {

    public EventRegistrationSettings toEventRegistrationSettings(EventRegistrationSettingsRequest request, Event event, List<Semester> allowedSemesters ) {
        return EventRegistrationSettings.builder()
                .event(event)
                .registrationStartDate(request.getRegistrationStartDate())
                .registrationEndDate(request.getRegistrationEndDate())
                .cancellationDeadline(request.getCancellationDeadline())
                .allowedSemesterList(allowedSemesters)
                .allowedPartList(request.getAllowedPartList())
                .allowedRoleList(request.getAllowedRoleList())
                .build();
    }
}
