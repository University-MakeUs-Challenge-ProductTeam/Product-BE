package umc.product.domain.semester.repository;


import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.SemesterPosition;

import java.util.List;
import java.util.Map;

public interface SemesterPositionDslRepository {
    Map<Long, SemesterPosition> findSemesterPositionListByMemberId(Long memberId);
}
