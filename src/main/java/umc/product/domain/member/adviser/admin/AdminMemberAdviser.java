package umc.product.domain.member.adviser.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.converter.response.MemberConverter;
import umc.product.domain.member.dto.request.admin.member.*;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.dto.response.admin.search.AdminMemberSearchPageResponse;
import umc.product.domain.member.dto.response.admin.search.AdminProfileDetailResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.repository.querydsl.MemberDslRepository;
import umc.product.domain.member.service.admin.AdminCodeService;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.domain.member.service.member.MemberService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.semester.service.SemesterPartService;
import umc.product.domain.semester.service.SemesterPositionService;
import umc.product.domain.semester.service.SemesterService;
import umc.product.domain.study.dto.response.admin.MemberSearchInfo;
import umc.product.domain.university.entity.University;
import umc.product.domain.university.service.UniversityService;
import umc.product.global.common.exception.RestApiException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static umc.product.domain.member.status.AuthErrorStatus.INVALID_ROLE;
import static umc.product.domain.semester.status.SemesterErrorStatus.EXIST_SEMESTER;
import static umc.product.domain.semester.status.SemesterErrorStatus.NOT_VALID_POSITION;

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
    private final MemberDslRepository memberRepository;

    private final MemberConverter memberConverter;

    public void registerMember(
            AdminRegisterListRequest request
    ) {
        //최근 기수의 직책, 파트만 등록
        Semester recentSemester = semesterService.findRecentSemester();
        //미리 모든 학교를 불러옴
        List<University> universityList = universityService.findUniversityList();
        //이름, 닉네임, 학교로 이미 존재하는 Member 검색(파트/직책 업데이트)
        List<Member> existedMemberList = adminMemberService.findExistedMemberList(request);
        //이름|닉네임|학교 로 묶기
        Set<String> existedKeys = existedMemberList.stream()
                .map(member -> member.getName() + "|" + member.getNickName() + "|" + member.getUniversity().getName())
                .collect(Collectors.toSet());
        //새로운 Member 필터링(새로 추가)
        List<AdminRegisterListRequest.AdminRegisterMemberRequest> newMemberRequestList = request.registerMemberList().stream()
                .filter(req -> !existedKeys.contains(req.name() + "|" + req.nickName() + "|" + req.universityName()))
                .collect(Collectors.toList());
        //새로운 Member Entity로 변환
        List<Member> newMemberList = adminMemberService.toMemberFromNewRegisterMember(newMemberRequestList, universityList);

        //새로운 Member의 Semester Part, Position 생성
        List<SemesterPart> newMemberSemesterPartList = semesterPartService.toSemesterPartForRegisterMember(request, newMemberList, recentSemester);
        List<SemesterPosition> newMemberSemesterPositionList = semesterPositionService.toSemesterPositionForRegisterMember(request, newMemberList, recentSemester);

        //기존 Member의 Semester Part, Position 생성
        List<SemesterPart> existMemberSemesterPartList = semesterPartService.toSemesterPartForRegisterMember(request, existedMemberList, recentSemester);
        List<SemesterPosition> existMemberSemesterPositionList = semesterPositionService.toSemesterPositionForRegisterMember(request, existedMemberList, recentSemester);

        //새로운 Member의 DB 등록
        List<Member> newRegisterMember = new ArrayList<>();
        if(!newMemberList.isEmpty()) {
            newRegisterMember = adminMemberService.saveRegisterNewMemberList(newMemberList, newMemberSemesterPartList, newMemberSemesterPositionList);
        }
        //기존 Member의 DB 반영(이미 엔티티 반영 되어 있음)
        if(!existedMemberList.isEmpty()) {
            adminMemberService.saveRegisterExistMemberList(existMemberSemesterPartList, existMemberSemesterPositionList);
        }

        //기존 Member와 완성된 새로운 Member를 합침
        List<Member> registerMemberList = new ArrayList<>();
        registerMemberList.addAll(newRegisterMember);
        registerMemberList.addAll(existedMemberList);

        //map 형식으로 만들어 Redis에 저장
        Map<String, Member> codeMap = adminCodeService.createAppCode(registerMemberList);
        adminCodeService.saveAppCode(codeMap);
    }

    public MemberIdResponse modifyMemberInfo(
            Member member,
            Long targetMemberId,
            AdminUpdateMemberProfileRequest request
    ) {
        Member targetMember = validateAndGetTargetMember(member, targetMemberId);

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
        Member targetMember = validateAndGetTargetMember(member, targetMemberId);

        Map<Long, Semester> semesterMap =  semesterService.findSemesterListForModify(
                request.semesterPartList(),
                AdminInsertSemesterPartListRequest.AdminInsertSemesterPartRequest::semesterId);

        List<Semester> semesterList = semesterMap.values().stream().collect(Collectors.toList());
        semesterPartService.validateSemesterPart(semesterList, targetMember);

        Map<Long, SemesterPosition> semesterPositionMap = semesterPositionService.findSemesterPositionMapByMemberId(member.getId());

        semesterList.forEach(semester -> {
            SemesterPosition semesterPosition = semesterPositionMap.get(semester.getId());

            if (semesterPosition != null) {  // 이미 해당 기수 직책이 설정되어 있음
                if (semesterPosition.getUniversityPosition() == null) {
                    semesterPosition.updateSemesterPosition(semester, "챌린저", semesterPosition.getCentralPosition());
                }
            } else {  // 해당 기수 직책이 설정되지 않은 경우
                semesterPosition = SemesterPosition.builder()
                        .member(targetMember)
                        .centralPosition(null)
                        .universityPosition("챌린저")
                        .semester(semester)
                        .build();
                targetMember.addSemesterPosition(List.of(semesterPosition)); // member에 추가
            }
        });

        List<SemesterPart> newSemesterPartList = semesterPartService.toSemesterPart(targetMember, request.semesterPartList(), semesterMap);
        adminMemberService.addSemesterPartList(targetMember, newSemesterPartList);

        return memberConverter.toMemberIdResponse(targetMember.getId());
    }

    public MemberIdResponse postMemberSemesterPosition(
            Member member,
            Long targetMemberId,
            AdminInsertSemesterPositionListRequest request
    ) {
        Member targetMember = validateAndGetTargetMember(member, targetMemberId);

        Map<Long, Semester> semesterMap =  semesterService.findSemesterListForModify(
                request.semesterPositionList(),
                AdminInsertSemesterPositionListRequest.AdminInsertSemesterPositionRequest::semesterId);

        List<Semester> semesterList = semesterMap.values().stream().collect(Collectors.toList());
        semesterPositionService.validateSemesterPosition(semesterList, targetMember);

        List<SemesterPosition> newSemesterPositionList = semesterPositionService.toSemesterPosition(targetMember, request.semesterPositionList(), semesterMap);
        adminMemberService.addSemesterPositionList(targetMember, newSemesterPositionList);
        return memberConverter.toMemberIdResponse(targetMember.getId());
    }

    public AdminMemberSearchPageResponse filterSearchMemberList(
            Member member,
            Pageable pageable,
            Long semesterId,
            Role role,
            Part part
    ) {
        Page<Member> memberList = adminMemberService.findMemberListByFilter(member, pageable, semesterId, role, part);
        Map<Long, String> codeMap = adminCodeService.getAppCodeMap(memberList.getContent());
        return memberConverter.toAdminMemberSearchListResponse(memberList, codeMap);
    }

    public AdminMemberSearchPageResponse searchMemberList(
            Member member,
            Pageable pageable,
            String searchString
    ) {
        Page<Member> memberList = adminMemberService.findMembersBySearchString(member, pageable, searchString);
        Map<Long, String> codeMap = adminCodeService.getAppCodeMap(memberList.getContent());
        return memberConverter.toAdminMemberSearchListResponse(memberList, codeMap);
    }

    public AdminProfileDetailResponse getProfileDetail(
            Long memberId
    ) {
        Member member = memberService.findById(memberId);
        return memberConverter.toAdminProfileDetailResponse(member);
    }

    private Member validateAndGetTargetMember(
            Member member,
            Long targetMemberId
    ) {
        Member targetMember = memberService.findById(targetMemberId);
        if (member.getRole().getPriority() >= targetMember.getRole().getPriority()) {
            throw new RestApiException(INVALID_ROLE);
        }
        return targetMember;
    }

    @Transactional(readOnly = true)
    public Page<MemberSearchInfo> searchMembers(Member adminMember, String keyword, Pageable pageable) {
        Role adminRole = adminMember.getRole();

        if (adminRole == Role.ADMIN || adminRole == Role.CENTRAL_ADMIN) {
            // 관리자, 중앙 운영진: 모든 멤버 대상
            return memberRepository.searchMembers(null, keyword, pageable);
        } else { // SCHOOL_ADMIN
            // 학교 관리자: 자기 학교 멤버만 대상
            Long universityId = adminMember.getUniversity().getId();
            return memberRepository.searchMembers(universityId, keyword, pageable);
        }
    }
}
