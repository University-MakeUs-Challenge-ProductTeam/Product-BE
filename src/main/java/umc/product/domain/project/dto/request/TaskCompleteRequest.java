package umc.product.domain.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.enums.Phase;

@Schema(description = "과제 완료를 위한 요청 DTO")
@Getter
@NoArgsConstructor
public class TaskCompleteRequest {

    @Schema(description = "과제 차수", example = "FIRST")
    private Phase phase;

    @Schema(description = "파트", example = "SPRING")
    private Part part;

    @Schema(description = "과제 완료 여부", example = "true")
    private boolean finishStatus;
}
