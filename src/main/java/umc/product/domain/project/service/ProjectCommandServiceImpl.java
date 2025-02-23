package umc.product.domain.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.project.dto.request.TaskCompleteRequest;
import umc.product.domain.project.dto.response.ProjectCompleteTaskResponse;
import umc.product.domain.task.service.TaskCommandService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectCommandServiceImpl implements ProjectCommandService {

    private final TaskCommandService taskCommandService;

    @Transactional
    public ProjectCompleteTaskResponse completeTask(Long projectId, TaskCompleteRequest request) {
        taskCommandService.completeTask(projectId, request.getPhase(), request.getPart(), request.isFinishStatus());
        return ProjectCompleteTaskResponse.from(projectId);
    }
}
