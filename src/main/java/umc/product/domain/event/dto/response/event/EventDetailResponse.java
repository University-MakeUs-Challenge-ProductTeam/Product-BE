package umc.product.domain.event.dto.response.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.event.entity.event.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
public class EventDetailResponse {

    @Schema(description = "행사 ID", example = "1")
    private Long eventId;

    @Schema(description = "행사 제목", example = "데모데이")
    private String title;

    @Schema(description = "행사 내용", example = "8기 데모데이를 개최합니다.")
    private String content;

    @Schema(description = "행사 유형 (UNIVERSITY: 학교, DEPARTMENT: 지부, CENTRAL: 중앙)", example = "UNIVERSITY")
    private EventType eventType;

    @Schema(description = "행사 일자", example = "2025-12-31")
    private LocalDate eventDate;

    @Schema(description = "행사 시간", example = "00:00")
    private LocalTime eventTime;

    @Schema(description = "행사 장소", example = "프론트원")
    private String location;

    @Schema(description = "행사 공지 시간", example = "2025-12-31T00:00")
    private LocalDateTime createdAt;

    @Schema(description = "행사 이미지 URL 목록", example = "[\"url1\", \"url2\"]")
    private List<String> images;

    private List<EventReviewResponse> reviews;

    @Schema(description = "행사 열람 체크 여부", example = "false")
    private Boolean isChecked;
}
