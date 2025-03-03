package umc.product.domain.semester.repository;


import umc.product.domain.member.dto.request.admin.AdminSemesterPositionRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;

import java.util.List;
import java.util.Map;

public interface SemesterPositionRepository  {
    List<SemesterPosition> findSemesterPositionList(Member targetMember, List<Long> positionIdList);
}
