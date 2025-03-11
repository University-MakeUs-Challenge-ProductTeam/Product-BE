package umc.product.domain.semester.repository;


import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.SemesterPosition;

import java.util.List;

public interface SemesterPositionRepository  {
    List<SemesterPosition> findSemesterPositionList(Member targetMember, List<Long> positionIdList);
    List<SemesterPosition> findAll();

    int count();
}
