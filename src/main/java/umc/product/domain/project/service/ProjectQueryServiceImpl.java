package umc.product.domain.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.project.dto.response.ProjectResponse;
import umc.product.domain.project.dto.response.ProjectTaskResponse;
import umc.product.domain.project.entity.Project;
import umc.product.domain.project.exception.ProjectException;
import umc.product.domain.project.repository.ProjectRepository;
import umc.product.global.common.exception.code.status.ProjectErrorStatus;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private final ProjectRepository projectRepository;

    @Override
    public Project findById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(()->
                new ProjectException(ProjectErrorStatus.PROJECT_NOT_FOUND));
    }

    @Override
    public List<ProjectTaskResponse> getTasks(Member member, Long projectId) {
        try {
            return projectRepository.getTasksQueryDSL(member, projectId);
        } catch (Exception e) {
            log.error("과제 조회 관련 에러, projectId: {}, memberId: {}", projectId, member.getId(), e);
            throw new ProjectException(ProjectErrorStatus.TASK_NOT_FOUND);
        }
    }

    @Override
    public List<ProjectResponse> getMyProjects(Member member) {
        try {
            return projectRepository.getMyProjects(member);
        } catch (Exception e) {
            log.error("프로젝트 조회 관련 에러, memberId: {}", member.getId(), e);
            throw new ProjectException(ProjectErrorStatus.PROJECT_NOT_FOUND);
        }
    }
}