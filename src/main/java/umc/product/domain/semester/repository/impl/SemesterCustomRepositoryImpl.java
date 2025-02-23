package umc.product.domain.semester.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.dto.request.member.MemberSignUpSemesterRequest;
import umc.product.domain.semester.entity.QSemester;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.repository.SemesterCustomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
@AllArgsConstructor
public class SemesterCustomRepositoryImpl implements SemesterCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSemester qSemester = QSemester.semester;
    @Override
    public List<Semester> findSemesters(List<MemberSignUpSemesterRequest> semesterList) {
        List<Long> semesterIdList = semesterList.stream()
                .map(MemberSignUpSemesterRequest::getSemesterId)
                .collect(Collectors.toList());

        return jpaQueryFactory
                .selectFrom(qSemester)
                .where(qSemester.id.in(semesterIdList))
                .fetch();
    }
}
