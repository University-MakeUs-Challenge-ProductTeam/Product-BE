package umc.product.domain.project.dto.response.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.project.dto.response.ProjectResponse;

import java.util.List;

@Schema(description = "프로젝트 목록 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ProjectListResponse {

    @Schema(description = "본인 참여 프로젝트 목록 리스트")
    private final List<ProjectResponse> projectResponseList;
}
