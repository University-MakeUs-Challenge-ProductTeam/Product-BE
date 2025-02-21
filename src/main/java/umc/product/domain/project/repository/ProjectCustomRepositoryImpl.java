package umc.product.domain.project.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.branch.entity.QBranch;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.member.entity.QMemberProject;
import umc.product.domain.member.entity.QMemberProjectPart;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.dto.response.*;
import umc.product.domain.project.entity.QProject;
import umc.product.domain.project.entity.QProjectPart;
import umc.product.domain.project.entity.mapping.QProjectTask;
import umc.product.domain.project.entity.mapping.QProjectUniversity;
import umc.product.domain.project.exception.ProjectException;
import umc.product.domain.task.entity.QTask;
import umc.product.domain.university.entity.QUniversity;
import umc.product.global.common.exception.code.status.ProjectErrorStatus;

import java.time.LocalDate;
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
    private final QBranch branch = QBranch.branch;
    private final QProjectUniversity projUniv = QProjectUniversity.projectUniversity;
    private final QUniversity univ = QUniversity.university;
    private final QMember member = QMember.member;

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
                .innerJoin(project.memberProjectList, memberProject)
                .where(memberProject.member.eq(member))
                .groupBy(project.id, project.title, project.slogan, project.logoUrl, project.prize)
                .fetch();
    }

    @Override
    public boolean isCorrectMember(Member member, Long projectId) {
        return jpaQueryFactory
                .selectFrom(memberProject)
                .where(memberProject.member.eq(member)
                        .and(memberProject.project.id.eq(projectId)))
                .fetchOne() != null; // boolean으로 변환되도록 != null 추가
    }

    @Override
    public ProjectInfoResponse getMyProject(Long projectId) {

        String duration = getDuration(projectId);
        List<String> universityNames = getUniversityNames(projectId);

        return jpaQueryFactory
                .select(new QProjectInfoResponse(
                        project.id,
                        project.title,
                        project.slogan,
                        project.description,
                        project.logoUrl,
                        project.imageUrl,
                        branch.name,  // todo : branch.semester로 변경 예정
                        Expressions.constant(universityNames),
                        Expressions.constant(duration),
                        project.publishStatus,
                        project.publishLink
                ))
                .from(project)
                .join(project.branch, branch)
                .where(project.id.eq(projectId))
                .fetchOne();
    }

    private List<String> getUniversityNames(Long projectId) { // 학교 목록 조회 메서드
        return jpaQueryFactory
                .select(univ.name)
                .from(projUniv)
                .join(projUniv.university, univ)
                .where(projUniv.project.id.eq(projectId))
                .fetch();
    }

    private String getDuration(Long projectId) { // startDate와 endDate를 통해 프로젝트 기간을 가져오는 메서드

        Tuple tuple = jpaQueryFactory
                .select(project.startDate, project.endDate)
                .from(project)
                .where(project.id.eq(projectId))
                .fetchOne();

        if (tuple == null) {
            throw new ProjectException(ProjectErrorStatus.PROJECT_DURATION_NOT_FOUND);
        }

        LocalDate startDate = tuple.get(project.startDate);
        LocalDate endDate = tuple.get(project.endDate);

        return startDate + " ~ " + (endDate != null ? endDate : "ing");
    }

    @Override
    public List<ProjectMemberResponse> getProjectMembers(Long projectId) {
        return jpaQueryFactory
                .from(memberProject)
                .join(memberProject.member, member)
                .join(memberProject.memberProjectPartList, memberProjectPart)
                .where(memberProject.project.id.eq(projectId))
                .transform(GroupBy.groupBy(member.id)
                        .list(Projections.constructor(
                                ProjectMemberResponse.class,
                                member.id,
                                member.nickName,
                                member.name,
                                GroupBy.list(memberProjectPart.part)
                        ))
                );
    }
}