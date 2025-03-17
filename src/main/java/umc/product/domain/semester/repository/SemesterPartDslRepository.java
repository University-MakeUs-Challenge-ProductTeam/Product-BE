package umc.product.domain.semester.repository;


import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.SemesterPart;

import java.util.List;
import java.util.Map;

public interface SemesterPartDslRepository {
    Map<Long, SemesterPart> findSemesterPartListByMemberId(Long memberId);

    boolean existSemesterList(List<Long> semesterIdList, Member member);
}
