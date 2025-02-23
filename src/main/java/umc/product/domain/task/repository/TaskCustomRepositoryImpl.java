package umc.product.domain.task.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.entity.QProjectTask;
import umc.product.domain.project.entity.enums.Phase;
import umc.product.domain.project.status.ProjectException;
import umc.product.domain.project.status.ProjectErrorStatus;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TaskCustomRepositoryImpl implements TaskCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QProjectTask projectTask = QProjectTask.projectTask;

    @Override
    public void modifyFinishStatus(Long projectId, Phase phase, Part part, boolean finishStatus) {

        long count = jpaQueryFactory
                .update(projectTask)
                .set(projectTask.finishStatus, finishStatus)  // finishStatus를 false -> true로 변경
                .where(
                        projectTask.project.id.eq(projectId)
                                .and(projectTask.task.phase.eq(phase))
                                .and(projectTask.task.part.eq(part))
                )
                .execute();

        if (count == 0) {
            log.warn("완료할 과제를 찾을 수 없는 에러, projectId: {}, phase: {}, part: {}", projectId, phase, part);
            throw new ProjectException(ProjectErrorStatus.TASK_NOT_FOUND);
        }
    }
}
