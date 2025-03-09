package umc.product.domain.semester.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.semester.entity.QSemesterPart;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.repository.SemesterPartRepository;

import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class SemesterPartRepositoryImpl implements SemesterPartRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSemesterPart qSemesterPart = QSemesterPart.semesterPart;


    @Override
    public List<SemesterPart> findAll() {
        return jpaQueryFactory
                .selectFrom(qSemesterPart)
                .fetch();
    }

    @Override
    public int count() {
        return jpaQueryFactory
                .selectFrom(qSemesterPart)
                .fetch()
                .size();
    }
}
