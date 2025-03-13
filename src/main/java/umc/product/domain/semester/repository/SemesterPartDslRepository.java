package umc.product.domain.semester.repository;


import umc.product.domain.semester.entity.SemesterPart;

import java.util.List;
import java.util.Map;

public interface SemesterPartDslRepository {
    Map<Long, SemesterPart> findSemesterPartListByMemberId(Long memberId);
}
