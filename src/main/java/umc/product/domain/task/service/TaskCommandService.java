package umc.product.domain.task.service;

import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.entity.enums.Phase;

public interface TaskCommandService {

    void completeTask(Long projectId, Phase phase, Part part, boolean finishStatus);
}
