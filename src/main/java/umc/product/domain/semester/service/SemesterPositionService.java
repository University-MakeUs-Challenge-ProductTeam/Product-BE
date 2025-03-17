package umc.product.domain.semester.service;

import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPositionListRequest;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;

import java.util.List;
import java.util.Map;

public interface SemesterPositionService {

    void validateSemesterPosition(List<Semester> semesterList, Member member);
    Map<Long, SemesterPosition> findSemesterPositionMapByMemberId(Long memberId);
    List<SemesterPosition> toSemesterPositionForRegisterMember(AdminRegisterListRequest request,
                                              List<Member> memberList,
                                              Semester recentSemester);
    List<SemesterPosition> toSemesterPosition(Member targetMember,
                                              List<AdminInsertSemesterPositionListRequest.AdminInsertSemesterPositionRequest> positionList,
                                              Map<Long, Semester> semesterMap);
}
