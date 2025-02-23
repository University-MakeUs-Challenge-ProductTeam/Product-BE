package umc.product.domain.semester.service;

import umc.product.domain.member.dto.response.member.MemberCodePropertiesResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;

import java.util.List;

public interface SemesterPositionService {
    public List<SemesterPosition> toSemesterPosition(Member member, Semester semester, List<MemberCodePropertiesResponse> adminCodePropertiesList);
}
