package umc.product.domain.member.service.admin;

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
import umc.product.domain.university.entity.University;

import java.util.List;
import java.util.Map;

public interface AdminMemberService {
    Member toAdminMember(AdminSignUpRequest request,
                         String avatarUrl,
                         String universityName);
    List<Member> findMembers(Member member,
                             Pageable pageable,
                             Long semesterId,
                             Role role,
                             Part part);
    List<Member> findMembersBySearchString(Member member,
                                           String searchString);
    List<Member> toMemberFromExcelMember(AdminRegisterListRequest request,
                                         List<University> universityList);
    List<Member> saveRegisterMembers(List<Member> memberList,
                                     List<SemesterPart> semesterPartList,
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
}
