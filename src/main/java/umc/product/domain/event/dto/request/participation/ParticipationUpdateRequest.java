package umc.product.domain.event.dto.request.participation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.participation.ParticipationStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationUpdateRequest {

    @Schema(description = "수정할 행사 참여 ID", example = "1")
    @NotNull(message = "행사 참여 ID는 필수입니다.")
    private Long participationId;

    @Schema(description = "참여 상태 (ATTENDED: 참석, ABSENT: 불참석)", example = "ATTENDED")
    @NotNull(message = "참여 상태는 필수입니다.")
    private ParticipationStatus status;
}
