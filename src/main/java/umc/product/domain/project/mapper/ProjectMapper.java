package umc.product.domain.project.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.project.dto.response.ProjectMemberResponse;
import umc.product.domain.project.dto.response.ProjectTaskResponse;
import umc.product.domain.project.dto.response.list.ProjectListResponse;
import umc.product.domain.project.dto.response.ProjectResponse;
import umc.product.domain.project.dto.response.list.ProjectMemberListResponse;
import umc.product.domain.project.dto.response.list.ProjectTaskListResponse;

import java.util.List;

@Component
public class ProjectMapper {

    public ProjectListResponse toProjectListResponse(List<ProjectResponse> projectList) {
        return ProjectListResponse.builder()
                .projectResponseList(projectList)
                .build();
    }

    public ProjectMemberListResponse toProjectMemberListResponse(List<ProjectMemberResponse> memberList) {
        return ProjectMemberListResponse.builder()
                .projectMemberResponseList(memberList)
                .build();
    }

    public ProjectTaskListResponse toProjectTaskListResponse(List<ProjectTaskResponse> taskList) {
        return ProjectTaskListResponse.builder()
                .projectTaskResponseList(taskList)
                .build();
    }
}
