package umc.product.domain.semester.service;

import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPartListRequest;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.entity.Study;

import java.util.List;
import java.util.Map;

public interface SemesterPartService {
    Map<Long,SemesterPart> findSemesterPartMapByMemberId(Long memberId);
  
    List<SemesterPart> toSemesterPart(Member targetMember,
                                      List<AdminInsertSemesterPartListRequest.AdminInsertSemesterPartRequest> partList,
                                      Map<Long, Semester> semesterMap);
    List<SemesterPart> toSemesterPart(AdminRegisterListRequest request,
                                      List<Member> memberList,
                                      Semester recentSemester);
    List<SemesterPart> getSemesterPartList(String part, Semester semester, List<Member> memberList);
    List<SemesterPart> getSemesterPartList(Study study, List<AdminStudyMemberRequest> memberRequestList, SemesterPart baseSemesterPart);

}
