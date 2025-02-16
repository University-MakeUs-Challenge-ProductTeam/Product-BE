package umc.product.domain.task.repository;

import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.enums.Phase;

public interface TaskCustomRepository {

    void modifyFinishStatus(Long projectId, Phase phase, Part part, boolean finishStatus);
}
