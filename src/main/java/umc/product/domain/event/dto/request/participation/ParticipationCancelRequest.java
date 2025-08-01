package umc.product.domain.event.dto.request.participation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationCancelRequest {
    @Schema(description = "참여 ID", example = "1")
    private Long participationId;

    @Schema(description = "취소 사유", example = "개인 사정으로 참석이 어렵습니다.")
    private String reason;
}
