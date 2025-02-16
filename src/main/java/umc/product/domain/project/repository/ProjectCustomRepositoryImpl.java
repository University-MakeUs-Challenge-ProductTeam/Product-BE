package umc.product.domain.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMemberProject;
import umc.product.domain.member.entity.QMemberProjectPart;
import umc.product.domain.project.dto.response.ProjectTaskResponse;
import umc.product.domain.project.dto.response.QProjectTaskResponse;
import umc.product.domain.project.entity.QProject;
import umc.product.domain.project.entity.mapping.QProjectTask;
import umc.product.domain.task.entity.QTask;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ProjectCustomRepositoryImpl implements ProjectCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QProject project = QProject.project;
    private final QMemberProject memberProject = QMemberProject.memberProject;
    private final QMemberProjectPart memberProjectPart = QMemberProjectPart.memberProjectPart;
    private final QTask task = QTask.task;
    private final QProjectTask projectTask = QProjectTask.projectTask;

    @Override
    public List<ProjectTaskResponse> getTasksQueryDSL(Member member, Long projectId) {

        return jpaQueryFactory
                .select(new QProjectTaskResponse(
                        task.part,
                        task.phase,
                        task.content,
                        projectTask.finishStatus
                ))
                .from(task)
                .join(memberProject)  // MemberProject와 조인 -> member, projectId 만족하는 칼럼 가져옴
                .on(memberProject.member.eq(member)
                        .and(memberProject.project.id.eq(projectId)))
                .join(memberProjectPart)  // MemberProjectPart와 조인 -> 프로젝트에서 사용자가 맡은 Part 가져옴
                .on(memberProjectPart.memberProject.eq(memberProject)
                        .and(task.part.eq(memberProjectPart.part)))
                .leftJoin(projectTask)  // ProjectTask의 finishStatus 조회
                .on(projectTask.task.eq(task)
                        .and(projectTask.project.id.eq(projectId)))
                .fetch();
    }
}
