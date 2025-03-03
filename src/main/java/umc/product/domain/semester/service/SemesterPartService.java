package umc.product.domain.semester.service;

import umc.product.domain.member.dto.request.admin.AdminPostSemesterPartRequest;
import umc.product.domain.member.dto.request.admin.AdminPostSemesterPositionRequest;
import umc.product.domain.member.dto.request.admin.AdminSemesterPartRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;

import java.util.List;
import java.util.Map;

public interface SemesterPartService {
    List<SemesterPart> toSemesterPart(Member targetMember, List<AdminPostSemesterPartRequest> partList, Map<Long, Semester> semesterMap);

}
