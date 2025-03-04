package umc.product.domain.semester.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.semester.entity.QSemesterPart;
import umc.product.domain.semester.repository.SemesterPartRepository;

@Repository
@Slf4j
@AllArgsConstructor
public class SemesterPartRepositoryImpl implements SemesterPartRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSemesterPart qSemesterPart = QSemesterPart.semesterPart;


}
