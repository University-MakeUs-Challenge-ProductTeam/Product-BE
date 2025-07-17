package umc.product.domain.member.service.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.dto.request.admin.member.AdminUpdateMemberProfileRequest;
import umc.product.domain.member.dto.request.admin.auth.AdminSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.university.entity.University;

import java.util.List;
import java.util.Map;

public interface AdminMemberService {
    List<Member> findExistedMemberList(AdminRegisterListRequest request);
    Member toAdminMember(AdminSignUpRequest request,
                         String avatarUrl,
                         String universityName);
    Page<Member> findMemberListByFilter(Member member,
                             Pageable pageable,
                             Long semesterId,
                             Role role,
                             Part part);
    Page<Member> findMembersBySearchString(Member member,
                                           Pageable pageable,
                                           String searchString);
    List<Member> toMemberFromNewRegisterMember(List<AdminRegisterListRequest.AdminRegisterMemberRequest> newMemberRequestList,
                                         List<University> universityList);
    List<Member> saveRegisterNewMemberList(List<Member> memberList,
                                     List<SemesterPart> semesterPartList,
                                     List<SemesterPosition> semesterPositionList);

    void saveRegisterExistMemberList(List<SemesterPart> semesterPartList,
                                     List<SemesterPosition> semesterPositionList);
    void modifyMemberInfo(Member targetMember,
                          University university,
                          AdminUpdateMemberProfileRequest request,
                          Map<Long, Semester> partSemesterMap,
                          Map<Long, Semester> positionSemesterMap);
    void addSemesterPositionList(Member targetMember,
                                 List<SemesterPosition> semesterPositionList);
    void addSemesterPartList(Member targetMember,
                             List<SemesterPart> semesterPartList);
  List<Member> getMemberList(List<AdminStudyMemberRequest> members);
  long countMemberByFilter(Member member, Long semesterId, Role role, Part part);
}
