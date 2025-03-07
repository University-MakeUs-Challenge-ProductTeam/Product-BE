package umc.product.domain.study.dto.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "스터디 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class StudyModifyRequest {

    @Schema(description = "스터디 이름", example = "피그말리온")
    @NotBlank(message = "수정할 스터디 이름은 비어있을 수 없습니다.")
    private String studyName;

    @Schema(description = "주차", example = "1")
    private int week;
}
