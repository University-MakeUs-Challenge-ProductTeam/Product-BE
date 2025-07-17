package umc.product.domain.event.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.event.entity.event.EventType;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminEventDetailResponse {
    @Schema(description = "행사 ID", example = "1")
    private Long eventId;

    @Schema(description = "행사 내용", example = "8기 데모데이를 개최 합니다.")
    private String content;

    @Schema(description = "행사 시작 시간", example = "2025-07-03T14:30:00")
    private LocalDateTime eventStartDate;

    @Schema(description = "행사 종료 시간", example = "2025-07-03T14:30:00")
    private LocalDateTime eventEndDate;

    @Schema(description = "행사 장소 (지도 검색 결과)", example = "서울특별시 마포구 마포대로 122")
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

    @Schema(description = "신청 시작 일시", example = "2025-08-01T09:00:00")
    private LocalDateTime registrationStartDate;

    @Schema(description = "신청 종료 일시", example = "2025-08-05T23:59:59")
    private LocalDateTime registrationEndDate;

    @Schema(description = "취소 가능 기한", example = "2025-08-03T23:59:59")
    private LocalDateTime cancellationDeadline;

    //todo 행사 후기 추가하기.
}
