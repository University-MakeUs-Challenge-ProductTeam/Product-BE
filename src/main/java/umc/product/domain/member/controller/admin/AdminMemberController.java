package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.admin.AdminMemberAdviser;
import umc.product.domain.member.dto.request.common.CommonCodeRequest;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberCodeResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "어드민 Member 공통 로직 API", description = "Admin Member 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/members")
public class AdminMemberController {
    private final AdminMemberAdviser adminMemberAdviser;

    @Operation(summary = "운영진 확인코드 발급 API", description = "운영진 확인코드 발급하는 API입니다. 권한을 부여해주세요.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생")
    })
    @PostMapping("/create/admin-code")
    public BaseResponse<MemberCodeResponse> createAdminCode(@CurrentMember Member member,
                                                            @Valid @RequestBody AdminCodeRequest request) {
        //todo: 학교 없으면 생성까지
        return BaseResponse.onSuccess(adminMemberAdviser.createAdminCode(request));
    }

    @Operation(summary = "챌린저 확인코드 발급 API", description = "챌린저 확인코드 발급하는 API입니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생")
    })
    @PostMapping("/create/challenger-code")
    public BaseResponse<MemberCodeResponse> createChallengerCode(@CurrentMember Member member,
                                                                 @Valid @RequestBody CommonCodeRequest request) {
        return BaseResponse.onSuccess(adminMemberAdviser.createChallengerCode(request));
    }

    @GetMapping("/")
    //파라미터 수정해야함
    public BaseResponse<AdminMemberListResponse> searchMembers(@CurrentMember Member member,
                                                               @RequestParam Integer page,
                                                               @RequestParam Integer size,
                                                               @RequestParam(required = false) String semester,
                                                               @RequestParam(required = false) Role role,
                                                               @RequestParam(required = false) String part) {
        return BaseResponse.onSuccess(adminMemberAdviser.searchMembers(member, PageRequest.of(page,size), semester, role, part));
    }

    @Operation(summary = "챌린저 OUT(삼진아웃) API", description = "챌린저 OUT(삼진아웃)하는 API입니다.")
    @PatchMapping("/{memberId}")
    public BaseResponse<MemberIdResponse> outChallenger(@CurrentMember Member member,
                                                        @PathVariable(name = "memberId") Long memberId) {
        return BaseResponse.onSuccess(adminMemberAdviser.outChallenger(memberId));
    }
}
