package umc.product.domain.semester.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.dto.request.admin.AdminSemesterPositionRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.QSemesterPosition;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.semester.repository.SemesterPositionRepository;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

import static umc.product.domain.semester.status.SemesterErrorStatus.NOT_MATCH_POSITION_MEMBER;

@Repository
@Slf4j
@AllArgsConstructor
public class SemesterPositionRepositoryImpl implements SemesterPositionRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QSemesterPosition qSemesterPosition = QSemesterPosition.semesterPosition;


    @Override
    public List<SemesterPosition> findSemesterPositionList(Member targetMember, List<Long> positionIdList) {
        List<SemesterPosition> positionList = jpaQueryFactory
                .selectFrom(qSemesterPosition)
                .where(qSemesterPosition.id.in(positionIdList))
                .fetch();

        boolean hasInvalidMember = positionList.stream()
                .anyMatch(position -> !position.getMember().equals(targetMember));

        if(hasInvalidMember) throw new RestApiException(NOT_MATCH_POSITION_MEMBER);
        return positionList;
    }


}
