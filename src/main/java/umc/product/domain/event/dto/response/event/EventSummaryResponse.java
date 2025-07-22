package umc.product.domain.event.dto.response.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.event.entity.event.EventType;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventSummaryResponse{
    @Schema(description = "행사 ID", example = "1")
    private Long eventId;
    
    @Schema(description = "행사 제목", example = "데모데이")
    private String title;

    @Schema(description = "행사 유형 (UNIVERSITY: 학교, DEPARTMENT: 지부, CENTRAL: 중앙)", example = "UNIVERSITY")
    private EventType eventType;
    
    @Schema(description = "행사 등록 시간", example = "2025-12-31")
    private LocalDateTime createdAt;

    @Schema(description = "행사 썸네일 URL", example = "thumbnail-url")
    private String thumbnail;
}
