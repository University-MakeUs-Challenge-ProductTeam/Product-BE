package umc.product.domain.study.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.study.entity.enums.PassStatus;

@Getter
@NoArgsConstructor
@Schema(description = "주차별 스터디 상태 설정 요청 DTO")
public class AdminWeeklyStatusRequest {
  @Schema(description = "설정할 상태", example = "PASS")
  @NotNull
  private PassStatus status; // // PENDING, PASS, OUT 중 하나
}
