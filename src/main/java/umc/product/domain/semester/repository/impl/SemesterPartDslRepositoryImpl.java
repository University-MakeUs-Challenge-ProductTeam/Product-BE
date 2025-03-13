package umc.product.domain.semester.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.QMember;
import umc.product.domain.semester.entity.QSemester;
import umc.product.domain.semester.entity.QSemesterPart;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.repository.SemesterPartDslRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
@AllArgsConstructor
public class SemesterPartDslRepositoryImpl implements SemesterPartDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSemesterPart qSemesterPart = QSemesterPart.semesterPart;
    private final QSemester qSemester = QSemester.semester;
    private final QMember qMember = QMember.member;

    @Override
    public Map<Long, SemesterPart> findSemesterPartListByMemberId(Long memberId) {
        List<SemesterPart> semesterPartList =  jpaQueryFactory
                .selectFrom(qSemesterPart)
                .join(qSemesterPart.semester, qSemester).fetchJoin()
                .leftJoin(qSemesterPart.member, qMember)
                .where(qMember.id.eq(memberId))
                .fetch();

        return semesterPartList.stream()
                .collect(Collectors.toMap(
                        semesterPart -> semesterPart.getSemester().getId(),
                        semesterPart -> semesterPart
                ));
    }
}
