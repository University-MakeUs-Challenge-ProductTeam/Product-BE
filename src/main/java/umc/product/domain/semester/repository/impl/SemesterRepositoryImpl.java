package umc.product.domain.semester.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.semester.entity.QSemester;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.repository.SemesterRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
@AllArgsConstructor
public class SemesterRepositoryImpl implements SemesterRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSemester qSemester = QSemester.semester;

    @Override
    public List<Semester> findSemesterList(List<Long> semesterIdList) {
        return jpaQueryFactory
                .selectFrom(qSemester)
                .where(qSemester.id.in(semesterIdList))
                .fetch();
    }

    @Override
    public Map<Long, Semester> findSemesterMap(List<Long> semesterIdList) {
        return jpaQueryFactory
                .selectFrom(qSemester)
                .where(qSemester.id.in(semesterIdList))
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        Semester::getId,
                        semester -> semester
                ));
    }


    @Override
    public Semester findRecentSemester() {
        return jpaQueryFactory
                .selectFrom(qSemester)
                .orderBy(qSemester.id.desc())
                .fetchFirst();
    }
}
