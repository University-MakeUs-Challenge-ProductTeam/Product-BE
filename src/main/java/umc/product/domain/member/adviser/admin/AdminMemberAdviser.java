package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.admin.member.*;
import umc.product.domain.member.dto.response.admin.search.AdminMemberSearchListResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.member.service.member.MemberService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.semester.service.SemesterPartService;
import umc.product.domain.semester.service.SemesterPositionService;
import umc.product.domain.semester.service.SemesterService;
import umc.product.domain.university.entity.University;
import umc.product.domain.university.service.UniversityService;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.dto.excel.ExcelMember;
import umc.product.global.util.ExcelFileUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static umc.product.global.common.exception.code.status.AuthErrorStatus.INVALID_ROLE;

@Component
@RequiredArgsConstructor
public class AdminMemberAdviser {
    private final AdminMemberService adminMemberService;
    private final MemberService memberService;
    private final UniversityService universityService;
    private final ExcelFileUtil excelFileUtil;
    private final SemesterService semesterService;
    private final SemesterPositionService semesterPositionService;
    private final SemesterPartService semesterPartService;

    private final MemberConverter memberConverter;

    public void registerMember(MultipartFile excel) {
        Semester recentSemester = semesterService.findRecentSemester();     //최근 기수의 직책만 등록
        List<University> universityList = universityService.findUniversityList();
        List<ExcelMember> excelMember = excelFileUtil.convertToExcelMember(excel, universityList);
        List<Member> memberList = adminMemberService.toMemberFromExcelMember(excelMember);
        List<SemesterPosition> semesterPositionList = semesterPositionService.toSemesterPosition(memberList, excelMember, recentSemester);
        adminMemberService.saveRegisterMembers(memberList, semesterPositionList);
    }

    public MemberIdResponse modifyMemberInfo(Member member, Long memberId, AdminUpdateMemberProfileRequest request) {
        Member targetMember = memberService.findById(memberId);
        if(member.getRole().getPriority() >= targetMember.getRole().getPriority()) throw new RestApiException(INVALID_ROLE);  //권한 체크
        University university = universityService.findUniversity(request.universityName());
        Map<Long, Semester> partSemesterMap = new HashMap<>();
        Map<Long, Semester> positionSemesterMap = new HashMap<>();

        if (!request.semesterPartList().isEmpty()) partSemesterMap = semesterService.findSemesterListForModify(request.semesterPartList(), AdminUpdateSemesterPartRequest::semesterId);
        if (!request.semesterPositionList().isEmpty()) positionSemesterMap = semesterService.findSemesterListForModify(request.semesterPositionList(), AdminUpdateSemesterPositionRequest::semesterId);

        adminMemberService.modifyMemberInfo(targetMember,university,request,partSemesterMap, positionSemesterMap);
        return memberConverter.toMemberIdResponse(targetMember.getId());
    }

    public MemberIdResponse postMemberSemesterPart(Member member, Long memberId, AdminInsertSemesterPartListRequest request) {
        Member targetMember = memberService.findById(memberId);
        if(member.getRole().getPriority() >= targetMember.getRole().getPriority()) throw new RestApiException(INVALID_ROLE);  //권한 체크

        Map<Long, Semester> semesterMap =  semesterService.findSemesterListForModify(request.semesterPartList(), AdminInsertSemesterPartRequest::semesterId);
        List<SemesterPart> newSemesterPartList = semesterPartService.toSemesterPart(targetMember, request.semesterPartList(), semesterMap);

        adminMemberService.addSemesterPartList(targetMember, newSemesterPartList);

        return memberConverter.toMemberIdResponse(targetMember.getId());
    }

    public MemberIdResponse postMemberSemesterPosition(Member member, Long memberId, AdminInsertSemesterPositionListRequest request) {
        Member targetMember = memberService.findById(memberId);
        if(member.getRole().getPriority() >= targetMember.getRole().getPriority()) throw new RestApiException(INVALID_ROLE);  //권한 체크

        Map<Long, Semester> semesterMap =  semesterService.findSemesterListForModify(request.semesterPositionList(), AdminInsertSemesterPositionRequest::semesterId);
        List<SemesterPosition> newSemesterPositionList = semesterPositionService.toSemesterPosition(targetMember, request.semesterPositionList(), semesterMap);

        adminMemberService.addSemesterPositionList(targetMember, newSemesterPositionList);

        return memberConverter.toMemberIdResponse(targetMember.getId());
    }

    public AdminMemberSearchListResponse filterSearchMembers(Member member, Pageable pageable, String semester, Role role, Part part) {
        List<Member> memberList = adminMemberService.findMembers(member, pageable, semester, role, part);
        return memberConverter.toAdminMemberListResponse(memberList);
    }

    public AdminMemberSearchListResponse searchMembers(Member member, String searchString) {
        List<Member> memberList = adminMemberService.findMembersBySearchString(member, searchString);
        return memberConverter.toAdminMemberListResponse(memberList);
    }
}
