package umc.product.domain.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.entity.enums.Phase;
import umc.product.domain.task.repository.TaskRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskCommandServiceImpl implements TaskCommandService {

    private final TaskRepository taskRepository;

    @Override
    public void completeTask(Long projectId, Phase phase, Part part, boolean finishStatus) {
        taskRepository.modifyFinishStatus(projectId, phase, part, finishStatus);
    }
}