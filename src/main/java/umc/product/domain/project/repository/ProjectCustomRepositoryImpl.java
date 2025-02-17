package umc.product.domain.project.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMemberProject;
import umc.product.domain.member.entity.QMemberProjectPart;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.dto.response.ProjectResponse;
import umc.product.domain.project.dto.response.ProjectTaskResponse;
import umc.product.domain.project.dto.response.QProjectResponse;
import umc.product.domain.project.dto.response.QProjectTaskResponse;
import umc.product.domain.project.entity.QProject;
import umc.product.domain.project.entity.QProjectPart;
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
    private final QProjectPart projectPart = QProjectPart.projectPart;

    @Override
    public List<ProjectTaskResponse> getTasks(Member member, Long projectId) {

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

    @Override
    public List<ProjectResponse> getMyProjects(Member member) {
        QMemberProject memberProjectCount = new QMemberProject("memberProjectCount"); // participantCount 구하기 위한 QMemberProject 객체(alias)

        return jpaQueryFactory
                .select(new QProjectResponse(
                        project.id,
                        project.title,
                        project.slogan,
                        project.logoUrl,
                        project.prize,
                        JPAExpressions  // frontPart 서브쿼리(WEB, IOS, ANDROID)
                                .select(projectPart.part)
                                .from(projectPart)
                                .where(
                                        projectPart.project.eq(project)
                                                .and(projectPart.part.in(Part.WEB, Part.IOS, Part.ANDROID))
                                )
                                .limit(1),
                        JPAExpressions  // serverPart 서브쿼리(SPRING, NODE)
                                .select(projectPart.part)
                                .from(projectPart)
                                .where(
                                        projectPart.project.eq(project)
                                                .and(projectPart.part.in(Part.SPRING, Part.NODE))
                                )
                                .limit(1),
                        JPAExpressions  // participantCount 서브쿼리(memberProject 카운트)
                                .select(memberProjectCount.count().castToNum(Integer.class))
                                .from(memberProjectCount)
                                .where(memberProjectCount.project.eq(project))
                ))
                .from(project)
                .innerJoin(project.memberProjects, memberProject)
                .where(memberProject.member.eq(member))
                .groupBy(project.id, project.title, project.slogan, project.logoUrl, project.prize)
                .fetch();
    }
}
