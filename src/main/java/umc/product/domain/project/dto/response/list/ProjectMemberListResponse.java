package umc.product.domain.project.dto.response.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.project.dto.response.ProjectMemberResponse;

import java.util.List;

@Schema(description = "프로젝트 인원 리스트 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ProjectMemberListResponse {

    @Schema(description = "프로젝트 참여 인원 리스트")
    private final List<ProjectMemberResponse> projectMemberResponseList;
}
