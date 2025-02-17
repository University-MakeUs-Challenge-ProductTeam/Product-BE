package umc.product.domain.project.repository;

import umc.product.domain.member.entity.Member;
import umc.product.domain.project.dto.response.ProjectInfoResponse;
import umc.product.domain.project.dto.response.ProjectMemberResponse;
import umc.product.domain.project.dto.response.ProjectResponse;
import umc.product.domain.project.dto.response.ProjectTaskResponse;

import java.util.List;

public interface ProjectCustomRepository {

    List<ProjectTaskResponse> getTasks(Member member, Long projectId);
    List<ProjectResponse> getMyProjects(Member member);
    boolean isCorrectMember(Member member, Long projectId);
    ProjectInfoResponse getMyProject(Long projectId);
    List<ProjectMemberResponse> getProjectMembers(Long projectId);
}
