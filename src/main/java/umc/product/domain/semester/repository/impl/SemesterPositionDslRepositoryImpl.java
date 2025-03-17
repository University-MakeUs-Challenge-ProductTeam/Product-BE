package umc.product.domain.semester.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.semester.entity.QSemester;
import umc.product.domain.semester.entity.QSemesterPosition;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.semester.repository.SemesterPositionDslRepository;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static umc.product.domain.semester.status.SemesterErrorStatus.NOT_MATCH_POSITION_MEMBER;

@Repository
@Slf4j
@AllArgsConstructor
public class SemesterPositionDslRepositoryImpl implements SemesterPositionDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSemesterPosition qSemesterPosition = QSemesterPosition.semesterPosition;
    private final QSemester qSemester = QSemester.semester;
    private final QMember qMember = QMember.member;


    @Override
    public Map<Long, SemesterPosition> findSemesterPositionListByMemberId(Long memberId) {
        List<SemesterPosition> semesterPositionList = jpaQueryFactory
                .selectFrom(qSemesterPosition)
                .join(qSemesterPosition.semester, qSemester).fetchJoin()
                .leftJoin(qSemesterPosition.member, qMember)
                .where(qMember.id.eq(memberId))
                .fetch();

        return semesterPositionList.stream()
                .collect(Collectors.toMap(
                        semesterPosition -> semesterPosition.getSemester().getId(),
                        semesterPosition -> semesterPosition
                ));
    }

}
