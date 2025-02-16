package umc.product.domain.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.enums.Phase;

import java.util.List;

@Schema(description = "프로젝트 과제 리스트 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ProjectTaskListResponse {

    @Schema(description = "프로젝트 과제 목록 리스트")
    private final List<ProjectTaskResponse> projectTaskResponses;

    public static ProjectTaskListResponse from(List<ProjectTaskResponse> recommendResponses){
        return new ProjectTaskListResponse(recommendResponses);
    }

    @Schema(description = "과제 정보 DTO")
    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProjectTaskResponse {

        @Schema(description = "파트", example = "SPRING")
        private Part part;

        @Schema(description = "과제 차수(1차, 2차, 3차)", example = "FIRST")
        private Phase phase;

        @Schema(description = "과제 내용", example = "SPRING 1차 과제 내용")
        private String content;

        @Schema(description = "완료 상태", example = "false")
        private boolean finishStatus;

        public static ProjectTaskResponse of(Part part,
                                             Phase phase,
                                             String content,
                                             boolean finishStatus) {
            return ProjectTaskResponse.builder()
                    .part(part)
                    .phase(phase)
                    .content(content)
                    .finishStatus(finishStatus)
                    .build();
        }
    }
}
