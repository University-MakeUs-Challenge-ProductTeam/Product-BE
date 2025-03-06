package umc.product.domain.member.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.member.adviser.admin.AdminMemberAdviser;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPartListRequest;
import umc.product.domain.member.dto.request.admin.member.AdminInsertSemesterPositionListRequest;
import umc.product.domain.member.dto.request.admin.member.AdminUpdateMemberProfileRequest;
import umc.product.domain.member.dto.response.admin.search.AdminMemberSearchListResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
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

    @Operation(summary = "회원 등록 API(공통)")
    @PostMapping(path = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<Void> registerMember(@CurrentMember Member member,
                                             @Valid @RequestPart("excel") MultipartFile excel) {
        adminMemberAdviser.registerMember(excel);
        return BaseResponse.onSuccess(null);
    }

    @Operation(summary = "프로필 수정 API", description = "프로필(이름, 닉네임, 학교, 직책, 기수/파트) 수정하는 API입니다.")
    @PatchMapping("/profile/{memberId}")
    public BaseResponse<MemberIdResponse> modifyMemberInfo(@CurrentMember Member member,
                                                           @PathVariable(name = "memberId") Long memberId,
                                                           @RequestBody AdminUpdateMemberProfileRequest request) {
        return BaseResponse.onSuccess(adminMemberAdviser.modifyMemberInfo(member, memberId, request));
    }

    @Operation(summary = "파트 추가 API", description = "파트 추가하는 API입니다.")
    @PostMapping("/profile/part/{memberId}")
    public BaseResponse<MemberIdResponse> postMemberSemesterPart(@CurrentMember Member member,
                                                           @PathVariable(name = "memberId") Long memberId,
                                                           @RequestBody AdminInsertSemesterPartListRequest request) {
        return BaseResponse.onSuccess(adminMemberAdviser.postMemberSemesterPart(member, memberId, request));
    }

    @Operation(summary = "직책 추가 API", description = "직책 추가하는 API입니다.")
    @PostMapping("/profile/position/{memberId}")
    public BaseResponse<MemberIdResponse> postMemberSemesterPosition(@CurrentMember Member member,
                                                           @PathVariable(name = "memberId") Long memberId,
                                                           @RequestBody AdminInsertSemesterPositionListRequest request) {
        return BaseResponse.onSuccess(adminMemberAdviser.postMemberSemesterPosition(member, memberId, request));
    }


    @Operation(summary = "사용자 필터링 검색 API", description = "사용자를 기수, 파트, 권한에따라 필터링 검색하는 API입니다.")
    @GetMapping("/filter")
    //파라미터 수정해야함
    public BaseResponse<AdminMemberSearchListResponse> filterSearchMembers(@CurrentMember Member member,
                                                                           @RequestParam Integer cursor,
                                                                           @RequestParam Integer size,
                                                                           @RequestParam(required = false) Long semesterId,
                                                                           @RequestParam(required = false) Role role,
                                                                           @RequestParam(required = false) Part part) {
        return BaseResponse.onSuccess(adminMemberAdviser.filterSearchMembers(member, PageRequest.of(cursor,size), semesterId, role, part));
    }

    @Operation(summary = "사용자 이름/닉네임 검색 API", description = "사용자를 이름/닉네임으로 검색하는 API입니다.")
    @GetMapping("/search")
    //파라미터 수정해야함
    public BaseResponse<AdminMemberSearchListResponse> searchMembers(@CurrentMember Member member,
                                                                     @RequestParam(required = false) String searchString) {
        return BaseResponse.onSuccess(adminMemberAdviser.searchMembers(member, searchString));
    }
}
