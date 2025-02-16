package umc.product.domain.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.task.repository.TaskRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskQueryServiceImpl implements TaskQueryService {

    private final TaskRepository taskRepository;
}
