package umc.product.domain.project.service;

import umc.product.domain.member.entity.Member;
import umc.product.domain.project.dto.response.ProjectResponse;
import umc.product.domain.project.dto.response.ProjectTaskResponse;
import umc.product.domain.project.entity.Project;

import java.util.List;

public interface ProjectQueryService {
    Project findById(Long projectId);
    List<ProjectTaskResponse> getTasks(Member member, Long projectId);
    List<ProjectResponse> getMyProjects(Member member);
}
