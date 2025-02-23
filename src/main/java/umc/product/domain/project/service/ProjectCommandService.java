package umc.product.domain.project.service;

import umc.product.domain.project.dto.request.TaskCompleteRequest;
import umc.product.domain.project.dto.response.ProjectCompleteTaskResponse;

public interface ProjectCommandService {

    ProjectCompleteTaskResponse completeTask(Long projectId, TaskCompleteRequest request);
}
