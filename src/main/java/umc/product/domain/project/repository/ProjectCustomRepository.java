package umc.product.domain.project.repository;

import umc.product.domain.member.entity.Member;
import umc.product.domain.project.dto.response.ProjectResponse;
import umc.product.domain.project.dto.response.ProjectTaskResponse;

import java.util.List;

public interface ProjectCustomRepository {

    List<ProjectTaskResponse> getTasks(Member member, Long projectId);
    List<ProjectResponse> getMyProjects(Member member);
}
