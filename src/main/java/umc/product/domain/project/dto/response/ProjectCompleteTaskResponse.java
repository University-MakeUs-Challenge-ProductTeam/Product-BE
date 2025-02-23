package umc.product.domain.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "프로젝트 과제 완료 시 반환 DTO")
@Getter
@Builder
public class ProjectCompleteTaskResponse {

    @Schema(description = "프로젝트 id", example = "1")
    private Long projectId;

    public static ProjectCompleteTaskResponse from(Long projectId){
        return ProjectCompleteTaskResponse.builder()
                .projectId(projectId)
                .build();
    }
}