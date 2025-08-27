package umc.product.domain.event.dto.response.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.event.entity.event.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class AdminEventDetailResponse {
    @Schema(description = "행사 ID", example = "1")
    private Long eventId;

    @Schema(description = "행사 내용", example = "8기 데모데이를 개최 합니다.")
    private String content;

    @Schema(description = "행사 날짜", example = "2025-07-03")
    private LocalDate eventDate;

    @Schema(description = "행사 시간", example = "14:30:00")
    private LocalTime eventTime;

    @Schema(description = "행사 장소", example = "서울특별시 마포구 마포대로 122")
    private String location;

    @Schema(description = "행사 썸네일 URL", example = "thumbnail-url")
    private String thumbnail;

    //todo 연결된 공지 추가하기.
//    @Schema(description = "연결된 공지")
//    private List<EventNotice> eventNotices;

    @Schema(description = "행사 타입(학교, 지부, 중앙 중 택 1)", example = "학교")
    private EventType eventType;

    @Schema(description = "현재 참여 인원 수", example = "100")
    private Integer participants;

    @Schema(description = "최대 참여 인원 제한 수", example = "120")
    private Integer maxParticipants;

    @Schema(description = "열람 인원 수", example = "80")
    private Long readCount;

    @Schema(description = "체크 인원 수", example = "50")
    private Long checkCount;
}
