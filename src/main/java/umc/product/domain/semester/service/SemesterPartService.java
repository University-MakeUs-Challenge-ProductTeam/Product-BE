package umc.product.domain.semester.service;

import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPartRequest;
import umc.product.domain.member.dto.request.admin.member.AdminRegisterRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;

import java.util.List;
import java.util.Map;

public interface SemesterPartService {
    List<SemesterPart> toSemesterPart(Member targetMember, List<AdminInsertSemesterPartRequest> partList, Map<Long, Semester> semesterMap);
    List<SemesterPart> toSemesterPart(AdminRegisterRequest request, List<Member> memberList, Semester recentSemester);
}
