package umc.product.domain.semester.service;

import umc.product.domain.member.dto.request.admin.AdminSemesterPositionRequest;
import umc.product.domain.member.dto.request.admin.AdminPostSemesterPositionRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.global.dto.excel.ExcelMember;

import java.util.List;
import java.util.Map;

public interface SemesterPositionService {
    List<SemesterPosition> toSemesterPosition(List<Member> memberList, List<ExcelMember> excelMemberList, Semester recentSemester);
    List<SemesterPosition> toSemesterPosition(Member targetMember, List<AdminPostSemesterPositionRequest> positionList, Map<Long, Semester> semesterMap);
}
