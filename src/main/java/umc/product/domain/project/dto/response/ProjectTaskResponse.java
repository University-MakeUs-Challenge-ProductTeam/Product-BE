package umc.product.domain.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.entity.enums.Phase;

@Schema(description = "프로젝트 과제 정보 응답 DTO")
@Getter
@Builder
public class ProjectTaskResponse {

    @Schema(description = "파트", example = "SPRING")
    private Part part;

    @Schema(description = "과제 차수(1차, 2차, 3차)", example = "FIRST")
    private Phase phase;

    @Schema(description = "과제 내용", example = "SPRING 1차 과제 내용")
    private String content;

    @Schema(description = "완료 상태", example = "false")
    private boolean finishStatus;

    @QueryProjection
    public ProjectTaskResponse(Part part, Phase phase, String content, boolean finishStatus) {
        this.part = part;
        this.phase = phase;
        this.content = content;
        this.finishStatus = finishStatus;
    }
}
