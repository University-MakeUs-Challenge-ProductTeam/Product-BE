package umc.product.domain.event.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.response.event.*;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventImage;
import umc.product.domain.event.entity.event.EventReview;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.SemesterPart;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventConverter {

    public EventIdResponse toEventIdResponse(Event event) {
        return EventIdResponse.builder()
                .eventId(event.getId())
                .build();
    }

    public <T> EventPagingResponse<T> toEventPagingResponse(Page<T> events) {
        return EventPagingResponse.<T>builder()
                .events(events.getContent())
                .page(events.getNumber())
                .totalPages(events.getTotalPages())
                .totalElements((int) events.getTotalElements())
                .isFirst(events.isFirst())
                .isLast(events.isLast())
                .build();
    }

    public AdminEventSummaryResponse toAdminEventSummaryResponse(Event event, Integer connectedNotices, Integer participants) {
        return AdminEventSummaryResponse.builder()
                .eventId(event.getId())
                .eventDate(event.getEventDate())
                .location(event.getLocation())
                .thumbnail(event.getThumbnail())
                .connectedNotices(connectedNotices)
                .eventType(event.getEventType())
                .participants(participants)
                .maxParticipants(event.getMaxParticipants())
                .build();
    }

    public AdminEventDetailResponse toAdminEventDetailResponse(Event event, Integer participants, Long readCount, Long checkCount) {
        return AdminEventDetailResponse.builder()
                .eventId(event.getId())
                .content(event.getContent())
                .eventDate(event.getEventDate())
                .eventTime(event.getEventTime())
                .location(event.getLocation())
                .thumbnail(event.getThumbnail())
                .eventType(event.getEventType())
                .participants(participants)
                .maxParticipants(event.getMaxParticipants())
                .checkCount(checkCount)
                .readCount(readCount)
                .build();
    }

    public EventSummaryResponse toEventSummaryResponse(Event event){
        return EventSummaryResponse.builder()
                .eventId(event.getId())
                .title(event.getTitle())
                .createdAt(event.getCreatedAt())
                .thumbnail(event.getThumbnail())
                .eventType(event.getEventType())
                .build();
    }

    public EventDetailResponse toEventDetailResponse(Event event, List<EventReviewResponse> reviews, Boolean isChecked){
        return EventDetailResponse.builder()
                .eventId(event.getId())
                .title(event.getTitle())
                .content(event.getContent())
                .eventType(event.getEventType())
                .eventDate(event.getEventDate())
                .eventTime(event.getEventTime())
                .location(event.getLocation())
                .createdAt(event.getCreatedAt())
                .images(toImageUrls(event.getImages()))
                .reviews(reviews)
                .isChecked(isChecked)
                .build();
    }

    public EventReviewResponse toEventReviewResponse(EventReview review) {

        Member writer = review.getWriter();

        //작성자의 활동 기수와 파트 정보
        SemesterPart latestSemesterPart = writer.getMemberSemesterPart().stream()
                .max(Comparator.comparing(SemesterPart::getCreatedAt))
                .orElse(null);


        return EventReviewResponse.builder()
                .id(review.getId())
                .name(writer.getNickName())
                .part(latestSemesterPart != null ? latestSemesterPart.getPart().name() : null)
                .semester(latestSemesterPart != null ? latestSemesterPart.getSemester().getName() : null)
                .createdAt(review.getCreatedAt())
                .content(review.getContent())
                .build();

    }

    private List<String> toImageUrls(List<EventImage> images) {
        return images.stream().map(EventImage::getUrl).toList();
    }
}
