package umc.product.domain.project.service;

import umc.product.domain.member.entity.Member;
import umc.product.domain.project.dto.response.ProjectInfoResponse;
import umc.product.domain.project.dto.response.list.ProjectListResponse;
import umc.product.domain.project.dto.response.list.ProjectMemberListResponse;
import umc.product.domain.project.dto.response.list.ProjectTaskListResponse;
import umc.product.domain.project.entity.Project;

public interface ProjectQueryService {
    Project findById(Long projectId);
    ProjectTaskListResponse getTasks(Member member, Long projectId);
    ProjectListResponse getMyProjects(Member member);
    ProjectInfoResponse getMyProject(Member member, Long projectId);
    ProjectMemberListResponse getProjectMembers(Long projectId);
}
