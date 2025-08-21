package umc.product.domain.event.dto.response.event;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventReviewResponse {

    private Long id;
    private String semester;
    private String part;
    private String name;
    private String content;
    private LocalDateTime createdAt;
}
