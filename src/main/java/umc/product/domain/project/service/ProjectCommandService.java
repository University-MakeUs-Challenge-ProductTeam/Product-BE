package umc.product.domain.project.service;

import umc.product.domain.project.dto.request.TaskCompleteRequest;

public interface ProjectCommandService {

    void completeTask(Long projectId, TaskCompleteRequest request);
}
