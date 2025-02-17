package umc.product.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.MemberAdminAdviser;
import umc.product.domain.member.dto.request.code.MemberChallengerCodeRequest;
import umc.product.domain.member.dto.request.code.MemberAdminCodeRequest;
import umc.product.domain.member.dto.request.MemberAdminSignUpRequest;
import umc.product.domain.member.dto.response.code.MemberCodeResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

import java.util.List;

@Tag(name = "어드민 Member API", description = "Admin Member 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class MemberAdminController {
    private final MemberAdminAdviser memberAdminAdviser;

    @Operation(summary = "ADMIN 회원가입 API", description = "최초 ADMIN 멤버 정보를 등록하는 API입니다")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생"),
            @ApiResponse(responseCode = "BRANCH001", description = "대학교가 지부랑 연결되어 있지 않을 경우 발생")
    })
    @PostMapping("/signup")
    public BaseResponse<MemberIdResponse> signUp(@Valid @RequestBody MemberAdminSignUpRequest request) {
        return BaseResponse.onSuccess(memberAdminAdviser.signUp(request));
    }
    @Operation(summary = "운영진 확인코드 발급 API", description = "운영진 확인코드 발급하는 API입니다. 권한을 부여해주세요.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생")
    })
    @PostMapping("/create/admin-code")
    public BaseResponse<MemberCodeResponse> createAdminCode(@CurrentMember Member member,
                                                            @Valid @RequestBody MemberAdminCodeRequest request) {
        //todo: 학교 없으면 생성까지
        return BaseResponse.onSuccess(memberAdminAdviser.createAdminCode(request));
    }

    @Operation(summary = "챌린저 확인코드 발급 API", description = "챌린저 확인코드 발급하는 API입니다.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "COMMON200", description = "성공"),
            @ApiResponse(responseCode = "UNIVERSITY001", description = "대학교명을 잘못 입력하였을 경우 발생")
    })
    @PostMapping("/create/challenger-code")
    public BaseResponse<MemberCodeResponse> createChallengerCode(@CurrentMember Member member,
                                                                 @Valid @RequestBody MemberChallengerCodeRequest request) {
        return BaseResponse.onSuccess(memberAdminAdviser.createChallengerCode(request));
    }

    @GetMapping("/")
    //파라미터 수정해야함
    public BaseResponse<List<MemberSearchResponse>> searchMembers(@CurrentMember Member member,
                                                                  @RequestParam Integer page,
                                                                  @RequestParam Integer size,
                                                                  @RequestParam(required = false) String semester,
                                                                  @RequestParam(required = false) Role role,
                                                                  @RequestParam(required = false) String part) {
        return BaseResponse.onSuccess(memberAdminAdviser.searchMembers(member, PageRequest.of(page,size), semester, role, part));
    }
}
