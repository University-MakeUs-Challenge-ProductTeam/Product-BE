package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.converter.response.MemberCodeConverter;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.response.admin.register.AdminRegisterListResponse;
import umc.product.domain.member.dto.request.admin.member.*;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.dto.response.admin.search.AdminMemberSearchListResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.admin.AdminCodeService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static umc.product.domain.member.status.AuthErrorStatus.INVALID_ROLE;

@Component
@RequiredArgsConstructor
public class AdminMemberAdviser {
    private final AdminMemberService adminMemberService;
    private final MemberService memberService;
    private final UniversityService universityService;
    private final SemesterService semesterService;
    private final SemesterPositionService semesterPositionService;
    private final SemesterPartService semesterPartService;
    private final AdminCodeService adminCodeService;

    private final MemberConverter memberConverter;

    public void registerMember(
            AdminRegisterListRequest request
    ) {
        //최근 기수의 직책, 파트만 등록
        Semester recentSemester = semesterService.findRecentSemester();
        //미리 모든 학교를 불러옴
        List<University> universityList = universityService.findUniversityList();

        List<Member> memberList = adminMemberService.toMemberFromExcelMember(request, universityList);
        List<SemesterPart> semesterPartList = semesterPartService.toSemesterPart(request, memberList, recentSemester);
        List<SemesterPosition> semesterPositionList = semesterPositionService.toSemesterPosition(request, memberList, recentSemester);
        List<Member> newMemberList = adminMemberService.saveRegisterMembers(memberList, semesterPartList, semesterPositionList);

        //map 형식으로 만들어 Redis에 저장
        Map<String, Member> codeMap = adminCodeService.createAppCode(newMemberList);
        adminCodeService.saveAppCode(codeMap);
    }

    public MemberIdResponse modifyMemberInfo(
            Member member,
            Long targetMemberId,
            AdminUpdateMemberProfileRequest request
    ) {
        Member targetMember = memberService.findById(targetMemberId);
        if(member.getRole().getPriority() >= targetMember.getRole().getPriority()) throw new RestApiException(INVALID_ROLE);  //권한 체크

        University university = universityService.findUniversity(request.universityName());
        Map<Long, Semester> partSemesterMap = new HashMap<>();
        Map<Long, Semester> positionSemesterMap = new HashMap<>();

        if (!request.semesterPartList().isEmpty()) partSemesterMap = semesterService.findSemesterListForModify(
                request.semesterPartList(),
                AdminUpdateMemberProfileRequest.AdminUpdateSemesterPartRequest::semesterId);

        if (!request.semesterPositionList().isEmpty()) positionSemesterMap = semesterService.findSemesterListForModify(
                request.semesterPositionList(),
                AdminUpdateMemberProfileRequest.AdminUpdateSemesterPositionRequest::semesterId);

        adminMemberService.modifyMemberInfo(targetMember,university,request,partSemesterMap, positionSemesterMap);
        return memberConverter.toMemberIdResponse(targetMember.getId());
    }

    public MemberIdResponse postMemberSemesterPart(
            Member member,
            Long targetMemberId,
            AdminInsertSemesterPartListRequest request
    ) {
        Member targetMember = memberService.findById(targetMemberId);
        if(member.getRole().getPriority() >= targetMember.getRole().getPriority()) throw new RestApiException(INVALID_ROLE);  //권한 체크

        Map<Long, Semester> semesterMap =  semesterService.findSemesterListForModify(
                request.semesterPartList(),
                AdminInsertSemesterPartListRequest.AdminInsertSemesterPartRequest::semesterId);

        List<SemesterPart> newSemesterPartList = semesterPartService.toSemesterPart(targetMember, request.semesterPartList(), semesterMap);

        adminMemberService.addSemesterPartList(targetMember, newSemesterPartList);

        return memberConverter.toMemberIdResponse(targetMember.getId());
    }

    public MemberIdResponse postMemberSemesterPosition(
            Member member,
            Long targetMemberId,
            AdminInsertSemesterPositionListRequest request
    ) {
        Member targetMember = memberService.findById(targetMemberId);
        if(member.getRole().getPriority() >= targetMember.getRole().getPriority()) throw new RestApiException(INVALID_ROLE);  //권한 체크

        Map<Long, Semester> semesterMap =  semesterService.findSemesterListForModify(
                request.semesterPositionList(),
                AdminInsertSemesterPositionListRequest.AdminInsertSemesterPositionRequest::semesterId);

        List<SemesterPosition> newSemesterPositionList = semesterPositionService.toSemesterPosition(targetMember, request.semesterPositionList(), semesterMap);

        adminMemberService.addSemesterPositionList(targetMember, newSemesterPositionList);

        return memberConverter.toMemberIdResponse(targetMember.getId());
    }

    public AdminMemberSearchListResponse filterSearchMemberList(
            Member member,
            Pageable pageable,
            Long semesterId,
            Role role,
            Part part
    ) {
        List<Member> memberList = adminMemberService.findMembers(member, pageable, semesterId, role, part);
        Map<Long, String> codeMap = adminCodeService.getAppCodeMap(memberList);
        return memberConverter.toAdminMemberSearchListResponse(memberList, codeMap);
    }

    public AdminMemberSearchListResponse searchMemberList(
            Member member,
            String searchString
    ) {
        List<Member> memberList = adminMemberService.findMembersBySearchString(member, searchString);
        Map<Long, String> codeMap = adminCodeService.getAppCodeMap(memberList);
        return memberConverter.toAdminMemberSearchListResponse(memberList, codeMap);
    }
}
