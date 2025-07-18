package umc.product.domain.event.service.admin.event;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.dto.request.event.EventUpdateRequest;
import umc.product.domain.event.dto.response.event.AdminEventSummaryResponse;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.member.entity.Member;

import java.util.List;

public interface AdminEventService {
    Event createEvent(Member writer, List<MultipartFile> eventImages, EventRequest eventRequest);
    Event updateEvent(Member writer, Long eventId, EventUpdateRequest request, List<MultipartFile> newImages);
    Event deleteEvent(Member writer, Long eventId);
    Event inquiryEventDetail(Long eventId);
    Page<AdminEventSummaryResponse> inquiryEventsByFilter(Integer month, String semester, EventType eventType, int page, int size);
}
