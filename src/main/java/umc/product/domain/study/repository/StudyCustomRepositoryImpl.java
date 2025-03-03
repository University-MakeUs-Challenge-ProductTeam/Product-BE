package umc.product.domain.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.semester.entity.QSemesterPart;
import umc.product.domain.study.dto.response.QStudyMemberResponse;
import umc.product.domain.study.dto.response.StudyMemberResponse;
import umc.product.domain.study.entity.QStudyAttendance;
import umc.product.domain.study.entity.QStudyMember;
import umc.product.domain.study.entity.Study;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StudyCustomRepositoryImpl implements StudyCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QStudyMember studyMember = QStudyMember.studyMember;
    private final QMember member = QMember.member;
    private final QStudyAttendance studyAttendance = QStudyAttendance.studyAttendance;
    private final QSemesterPart semesterPart = QSemesterPart.semesterPart;

    @Override
    public List<StudyMemberResponse> getStudyMembers(Study study) {

        return jpaQueryFactory
                .select(new QStudyMemberResponse(
                        member.id,
                        member.university.name,
                        member.nikeName,
                        studyAttendance.checkStatus.stringValue()
                ))
                .from(studyMember)
                .join(studyMember.semesterPart, semesterPart)
                .join(semesterPart.member, member)
                .leftJoin(studyAttendance)
                .on(studyAttendance.studyMember.eq(studyMember)
                        .and(studyAttendance.week.eq(study.getCurrentWeek())))
                .where(studyMember.study.id.eq(study.getId()))
                .fetch();
    }
}
