package umc.product.domain.event.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.converter.EventConverter;
import umc.product.domain.event.dto.request.EventRequest;
import umc.product.domain.event.dto.response.EventIdResponse;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.service.admin.AdminEventService;
import umc.product.domain.member.entity.Member;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminEventAdviser {

    private final AdminEventService adminEventService;
    private final EventConverter eventConverter;

    public EventIdResponse createEvent(Member writer, List<MultipartFile> eventImages, EventRequest request) {
        Event event = adminEventService.createEvent(writer, eventImages, request);
        return eventConverter.toEventIdResponse(event);
    }
}
