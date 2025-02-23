package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.admin.AdminMemberAdviser;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.member.MemberCodeResponse;
import umc.product.domain.member.dto.response.member.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "어드민 Member 공통 로직 API", description = "Admin Member 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/members")
public class AdminMemberController {
    private final AdminMemberAdviser adminMemberAdviser;

    @Operation(summary = "관리자 페이지용 학교코드(학교계정) 발급 API")
    @PostMapping("/create/university-code")
    public BaseResponse<MemberCodeResponse> createUniversityAdminCode(@CurrentMember Member member,
                                                            @Valid @RequestBody AdminCodeRequest request) {
        return BaseResponse.onSuccess(adminMemberAdviser.createUniversityAdminCode(request));
    }

    @Operation(summary = "앱용 운영진 확인코드 발급 API")
    @PostMapping("/create/admin-code")
    public BaseResponse<MemberCodeResponse> createAdminCode(@CurrentMember Member member,
                                                            @Valid @RequestBody AdminCodeRequest request) {
        return BaseResponse.onSuccess(adminMemberAdviser.createAdminCode(request));
    }

    @Operation(summary = "앱용 챌린저 확인코드 발급 API", description = "앱용 챌린저 확인코드 발급하는 API입니다.")
    @PostMapping("/create/challenger-code")
    public BaseResponse<MemberCodeResponse> createChallengerCode(@CurrentMember Member member,
                                                                 @Valid @RequestBody AdminCodeRequest request) {
        return BaseResponse.onSuccess(adminMemberAdviser.createChallengerCode(request));
    }

    @Operation(summary = "사용자 필터링 검색 API", description = "사용자를 기수, 파트, 권한에따라 필터링 검색하는 API입니다.")
    @GetMapping("/filter")
    //파라미터 수정해야함
    public BaseResponse<AdminMemberListResponse> filterSearchMembers(@CurrentMember Member member,
                                                               @RequestParam Integer page,
                                                               @RequestParam Integer size,
                                                               @RequestParam(required = false) String semester,
                                                               @RequestParam(required = false) Role role,
                                                               @RequestParam(required = false) Part part) {
        return BaseResponse.onSuccess(adminMemberAdviser.filterSearchMembers(member, PageRequest.of(page,size), semester, role, part));
    }

    @Operation(summary = "사용자 이름/닉네임 검색 API", description = "사용자를 이름/닉네임으로 검색하는 API입니다.")
    @GetMapping("/search")
    //파라미터 수정해야함
    public BaseResponse<AdminMemberListResponse> searchMembers(@CurrentMember Member member,
                                                               @RequestParam(required = false) String searchString) {
        return BaseResponse.onSuccess(adminMemberAdviser.searchMembers(searchString));
    }

    @Operation(summary = "챌린저 프로필 수정 API", description = "챌린저 프로필(이름, 닉네임, 학교, 직책, 기수/파트) 수정하는 API입니다.")
    @PatchMapping("/modify/{memberId}")
    public BaseResponse<MemberIdResponse> modifyMemberInfo(@CurrentMember Member member,
                                                           @PathVariable(name = "memberId") Long memberId) {
        return null;
    }

    @Operation(summary = "챌린저 OUT(삼진아웃) API", description = "챌린저 OUT(삼진아웃)하는 API입니다.")
    @PatchMapping("/{memberId}")
    public BaseResponse<MemberIdResponse> outChallenger(@CurrentMember Member member,
                                                        @PathVariable(name = "memberId") Long memberId) {
        return BaseResponse.onSuccess(adminMemberAdviser.outChallenger(memberId));
    }
}
