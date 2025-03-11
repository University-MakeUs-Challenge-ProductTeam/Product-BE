package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.admin.AdminMemberAdviser;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPartListRequest;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPositionListRequest;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.dto.request.admin.member.AdminUpdateMemberProfileRequest;
import umc.product.domain.member.dto.response.admin.search.AdminMemberSearchPageResponse;
import umc.product.domain.member.dto.response.admin.search.AdminProfileDetailResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.search.MemberProfileDetailResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "운영진용(Web) Member API", description = "운영진용(Web) Member Member 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/members")
public class AdminMemberController {
    private final AdminMemberAdviser adminMemberAdviser;

    @Operation(summary = "회원 등록 API(공통)", description = """
            사용자를 등록하고 코드를 응답하는 API 입니다.프론트에서 엑셀파일을 파싱하여 request body로 넘겨주세요\n
            part : ANDROID,IOS,SPRING,NODE,DESIGN,WEB,PLAN(없다면 null)\n
            권한 부여 로직 (universityPosition, centralPosition의 명칭으로 정해짐) -> 요청시에는 Role이 아닌 String(회장, 부회장..)\n
            universityPosition : 회장,부회장(SCHOOL_ADMIN) | ~파트장, 홍보팀장 etc..→ UNIVERSITY_STAFF(없다면 null로 요청)\n
            centralPosition : 총괄, 부총괄(ADMIN) | 나머지 직책(CENTRAL_ADMIN) (없다면 null로 요청)""")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "회원 등록 성공"
            )
    })
    @PostMapping(path = "/register")
    public BaseResponse<Void> registerMember(
            @RequestBody AdminRegisterListRequest request
    ) {
        adminMemberAdviser.registerMember(request);
        return BaseResponse.onSuccess(null);
    }

    @Operation(summary = "프로필 수정 API", description = "프로필(이름, 닉네임, 학교, 직책, 기수/파트) 수정하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "관리자에 의한 프로필 수정 성공"
            )
    })
    @Parameters({
            @Parameter(name = "memberId", description = "프로필을 수정하고 싶은 member의 id")
    })
    @PatchMapping("/profile/{memberId}")
    public BaseResponse<MemberIdResponse> modifyMemberInfo(
            @CurrentMember Member member,
            @PathVariable(name = "memberId") Long memberId,
            @RequestBody AdminUpdateMemberProfileRequest request
    ) {
        return BaseResponse.onSuccess(adminMemberAdviser.modifyMemberInfo(member, memberId, request));
    }

    @Operation(summary = "파트 추가 API", description = "파트 추가하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "관리자에 의한 파트 추가 성공"
            )
    })
    @Parameters({
            @Parameter(name = "memberId", description = "파트 추가하고 싶은 member의 id")
    })
    @PostMapping("/profile/part/{memberId}")
    public BaseResponse<MemberIdResponse> postMemberSemesterPart(
            @CurrentMember Member member,
            @PathVariable(name = "memberId") Long memberId,
            @RequestBody AdminInsertSemesterPartListRequest request
    ) {
        return BaseResponse.onSuccess(adminMemberAdviser.postMemberSemesterPart(member, memberId, request));
    }

    @Operation(summary = "직책 추가 API", description = "직책 추가하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "관리자에 의한 직책 추가 성공"
            )
    })
    @Parameters({
            @Parameter(name = "memberId", description = "직책 추가하고 싶은 member의 id")
    })
    @PostMapping("/profile/position/{memberId}")
    public BaseResponse<MemberIdResponse> postMemberSemesterPosition(
            @CurrentMember Member member,
            @PathVariable(name = "memberId") Long memberId,
            @RequestBody AdminInsertSemesterPositionListRequest request
    ) {
        return BaseResponse.onSuccess(adminMemberAdviser.postMemberSemesterPosition(member, memberId, request));
    }


    @Operation(summary = "사용자 필터링 검색 API", description = "사용자를 기수, 파트, 권한에따라 필터링 검색하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용자 필터링 검색 성공"
            )
    })
    @Parameters({
            @Parameter(name = "cursor", description = "page의 위치"),
            @Parameter(name = "size", description = "받고 싶은 page 당 size(통일해주세요)"),
            @Parameter(name = "semesterId", description = "검색하려는 기수의 id(8기->8, 7기->7...)"),
            @Parameter(name = "role", description = "검색하려는 사용자의 권한"),
            @Parameter(name = "part", description = "검색하려는 사용자의 진행 or 완료된 파트")
    })
    @GetMapping("/filter")
    public BaseResponse<AdminMemberSearchPageResponse> filterSearchMemberList(
            @CurrentMember Member member,
            @RequestParam Integer cursor,
            @RequestParam Integer size,
            @RequestParam(required = false) Long semesterId,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Part part
    ) {
        return BaseResponse.onSuccess(adminMemberAdviser.filterSearchMemberList(member, PageRequest.of(cursor,size), semesterId, role, part));
    }

    @Operation(summary = "사용자 이름/닉네임 검색 API", description = "사용자를 이름/닉네임으로 검색하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용자 이름 또는 닉네임으로 검색 성공"
            )
    })
    @Parameters({
            @Parameter(name = "cursor", description = "page의 위치"),
            @Parameter(name = "size", description = "받고 싶은 page 당 size(통일해주세요)"),
            @Parameter(name = "searchString", description = "검색하고 싶은 사용자의 이름 또는 닉네임"),
    })
    @GetMapping("/search")
    public BaseResponse<AdminMemberSearchPageResponse> searchMemberList(
            @CurrentMember Member member,
            @RequestParam Integer cursor,
            @RequestParam Integer size,
            @RequestParam(required = false) String searchString
    ) {
        return BaseResponse.onSuccess(adminMemberAdviser.searchMemberList(member, PageRequest.of(cursor, size), searchString));
    }

    @Operation(summary = "사용자 프로필 세부사항 조회(web) API", description = "웹에서 사용자의 Id로 프로필의 세부사항을 조회하는 API 입니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용자 프로필 조회 성공"
            )
    })
    @Parameters({
            @Parameter(name = "memberId", description = "프로필 세부사항을 조회하고 싶은 사용자의 Id"),
    })
    @GetMapping("/profile/detail/{memberId}")
    public BaseResponse<AdminProfileDetailResponse> getProfileDetail(
            @PathVariable(name = "memberId") Long memberId) {
        return BaseResponse.onSuccess(adminMemberAdviser.getProfileDetail(memberId));
    }
}
