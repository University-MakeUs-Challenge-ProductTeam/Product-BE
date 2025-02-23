package umc.product.domain.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.project.dto.response.ProjectInfoResponse;
import umc.product.domain.project.dto.response.ProjectMemberResponse;
import umc.product.domain.project.dto.response.list.ProjectListResponse;
import umc.product.domain.project.dto.response.list.ProjectMemberListResponse;
import umc.product.domain.project.dto.response.list.ProjectTaskListResponse;
import umc.product.domain.project.entity.Project;
import umc.product.domain.project.mapper.ProjectMapper;
import umc.product.domain.project.status.ProjectException;
import umc.product.domain.project.repository.ProjectRepository;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.common.exception.code.status.GlobalErrorStatus;
import umc.product.domain.project.status.ProjectErrorStatus;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Project findById(Long projectId) {
        return projectRepository.findById(projectId).orElseThrow(()->
                new ProjectException(ProjectErrorStatus.PROJECT_NOT_FOUND));
    }

    @Override
    public ProjectTaskListResponse getTasks(Member member, Long projectId) {
        try {
            return projectMapper.toProjectTaskListResponse(projectRepository.getTasks(member, projectId));
        } catch (Exception e) {
            log.error("과제 조회 관련 에러, projectId: {}, memberId: {}", projectId, member.getId(), e);
            throw new ProjectException(ProjectErrorStatus.TASK_NOT_FOUND);
        }
    }

    @Override
    public ProjectListResponse getMyProjects(Member member) {
        try {
            return projectMapper.toProjectListResponse(projectRepository.getMyProjects(member));
        } catch (Exception e) {
            log.error("프로젝트 조회 관련 에러, memberId: {}", member.getId(), e);
            throw new ProjectException(ProjectErrorStatus.PROJECT_NOT_FOUND);
        }
    }

    @Override
    public ProjectInfoResponse getMyProject(Member member, Long projectId) {
        try {
            if (!projectRepository.isCorrectMember(member, projectId)) {
                log.error("사용자가 참여하지 않은 프로젝트 조회 시도, memberId: {}, projectId: {}", member.getId(), projectId);
                throw new RestApiException(GlobalErrorStatus._FORBIDDEN);
            }
            return projectRepository.getMyProject(projectId);
        } catch (RestApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("프로젝트 조회 관련 에러, memberId: {}, projectId: {}", member.getId(), projectId, e);
            throw new ProjectException(ProjectErrorStatus.PROJECT_NOT_FOUND);
        }
    }

    @Override
    public ProjectMemberListResponse getProjectMembers(Long projectId) {

        projectRepository.findById(projectId)
                .orElseThrow(() -> {
                    log.error("프로젝트 조회 관련 에러, projectId: {}", projectId);
                    return new ProjectException(ProjectErrorStatus.PROJECT_NOT_FOUND);
                });

        List<ProjectMemberResponse> memberList = projectRepository.getProjectMembers(projectId);

        if (memberList.isEmpty()) {
            log.error("해당 프로젝트에 참여한 사용자가 없는 에러, projectId: {}", projectId);
            throw new ProjectException(ProjectErrorStatus.PROJECT_MEMBER_NOT_FOUND);
        }
        return projectMapper.toProjectMemberListResponse(memberList);
    }
}