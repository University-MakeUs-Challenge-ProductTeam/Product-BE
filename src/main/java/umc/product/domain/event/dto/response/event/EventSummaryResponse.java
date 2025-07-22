package umc.product.domain.event.dto.response.event;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.event.entity.event.EventType;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventSummaryResponse{
    private Long eventId;
    private String title;
    private EventType eventType;
    private LocalDateTime createdAt;
}
