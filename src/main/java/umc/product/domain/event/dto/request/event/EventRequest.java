package umc.product.domain.event.dto.request.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.dto.request.form.EventFormRequest;
import umc.product.domain.event.entity.event.EventType;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @Schema(description = "행사 제목", example = "8기 데모데이")
    @NotBlank(message = "행사 제목은 필수 입력값입니다.")
    private String title;

    @Schema(description = "행사 내용", example = "8기 데모데이를 개최 합니다.")
    @NotBlank(message = "행사 내용은 필수 입력값입니다.")
    private String content;

    @Schema(description = "행사 타입(학교, 지부, 중앙 중 택 1)", example = "학교")
    @NotBlank(message = "행사 타입은 필수 입력값입니다.")
    private EventType eventType;

    @Schema(description = "학기 아이디", example = "1")
    private Long semesterId;

    @Schema(description = "행사 시작 시간", example = "2025-07-03T14:30:00")
    @Future(message = "행사 시작 시간의 경우, 과거 날짜를 선택할 수 없습니다.")
    private LocalDateTime eventStartDate;

    @Schema(description = "행사 종료 시간", example = "2025-07-03T14:30:00")
    @Future(message = "행사 종료 시간의 경우, 과거 날짜를 선택할 수 없습니다.")
    private LocalDateTime eventEndDate;

    @Schema(description = "행사 장소 (지도 검색 결과)", example = "서울특별시 마포구 마포대로 122")
    @NotBlank(message = "행사 장소는 필수 입력값입니다.")
    private String location;

    @Schema(description = "최대 인원", example = "120")
    private Integer maxParticipants;

    @Schema(description = "행사 신청 폼")
    private EventFormRequest form;

    @Schema(description = "행사 신청 설정 정보")
    private EventRegistrationSettingsRequest registrationSettings;
}
