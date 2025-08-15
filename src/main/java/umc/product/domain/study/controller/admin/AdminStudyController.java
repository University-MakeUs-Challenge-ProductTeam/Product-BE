package umc.product.domain.study.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.member.adviser.admin.AdminMemberAdviser;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.study.adviser.admin.AdminStudyAdviser;
import umc.product.domain.study.dto.request.admin.AdminStudyModifyRequest;
import umc.product.domain.study.dto.request.admin.AdminStudyRequest;
import umc.product.domain.study.dto.request.admin.AdminWeeklyStatusRequest;
import umc.product.domain.study.dto.response.admin.AdminMemberSearchResponse;
import umc.product.domain.study.dto.response.admin.AdminStudyListResponse;
import umc.product.domain.study.dto.response.admin.AdminStudyMemberStatusResponse;
import umc.product.domain.study.dto.response.admin.MemberSearchInfo;
import umc.product.domain.study.dto.response.admin.MemberWorkbookResponse;
import umc.product.domain.study.dto.response.admin.StudyInfo;
import umc.product.domain.study.dto.response.admin.WeeklyStatusCommonResponse;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "관리자용(웹) STUDY API", description = "관리자용(웹) 스터디 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/studies")
@Validated
public class AdminStudyController {

    private final AdminStudyAdviser adminStudyAdviser;
    private final AdminMemberAdviser adminMemberAdviser;

    @PostMapping()
    @Operation(summary = "스터디 생성 API", description = "관리자가 스터디를 생성하는 API입니다. 참여 인원은 최대 5명입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 생성 성공"
            )
    })
    public BaseResponse<StudyCommonResponse> createStudy(@Valid @RequestBody AdminStudyRequest request) {
        // 스터디를 생성하면서 StudyMember, StudyAttendance, ChecklistStudyMember 같이 생성
        return BaseResponse.onSuccess(adminStudyAdviser.createStudy(request));
    }

    @PatchMapping("/{studyId}")
    @Operation(summary = "스터디 수정 API(미완 - 90% 완료)", description = "관리자가 스터디를 수정하는 API입니다. 스터디 소속, 스터디 참여 인원, 스터디장만 변경 가능합니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 수정 성공"
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다")
    })
    public BaseResponse<StudyCommonResponse> modifyStudy(
            @CurrentMember Member member,
            @Valid @RequestBody AdminStudyModifyRequest request,
            @PathVariable Long studyId) {
        // 스터디 타입(SCHOOL, BRANCH), 스터디 역할(LEADER, CHALLENGER), 스터디 참여 인원만 변경 가능
        return BaseResponse.onSuccess(adminStudyAdviser.modifyStudy(studyId, request));
    }

    @DeleteMapping("/{studyId}")
    @Operation(summary = "스터디 삭제 API", description = "관리자가 스터디를 삭제하는 API입니다.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "스터디 삭제 성공"
            )
    })
    @Parameters({
            @Parameter(name = "studyId", description = "스터디 id, path variable 입니다")
    })
    public BaseResponse<StudyCommonResponse> deleteStudy(@PathVariable Long studyId) {
        // 스터디를 삭제하면서 StudyMember, StudyUniversity, StudyAttendance, ChecklistStudyMember 같이 삭제
        return BaseResponse.onSuccess(adminStudyAdviser.deleteStudy(studyId));
    }

    @GetMapping
    @Operation(summary = "스터디 목록 조회 API", description = "운영진 권한별로 스터디 목록을 검색, 필터링, 페이징하여 조회합니다.")
    public BaseResponse<AdminStudyListResponse> getStudyList(
        @CurrentMember Member adminMember,
        @RequestParam(required = false) Long semesterId,
        @RequestParam(required = false) Part part,
        @RequestParam(required = false) String keyword,
        @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<StudyInfo> studyPage = adminStudyAdviser.getStudyList(adminMember, semesterId, part, keyword, pageable);
        return BaseResponse.onSuccess(AdminStudyListResponse.from(studyPage));
    }

    @GetMapping("/{studyId}/members/status")
    @Operation(summary = "스터디 세부 조회 - 스터디원별 체크리스트 현황 조회 API", description = "특정 스터디의 모든 멤버에 대한 주차별 체크리스트 수행 현황을 조회합니다.")
    public BaseResponse<AdminStudyMemberStatusResponse> getAdminStudyMembersStatus(@PathVariable Long studyId) {
        AdminStudyMemberStatusResponse response = adminStudyAdviser.getStudyMembersStatus(studyId);
        return BaseResponse.onSuccess(response);
    }

    @PostMapping("/study-members/{studyMemberId}/weeks/{week}/status")
    @Operation(summary = "특정인원 전주차 체크리스트 현황 조회 - 주차별 상태 설정 API", description = "특정 스터디원의 특정 주차 상태를 PASS 또는 OUT으로 설정합니다.(OUT 부여) 상태는 PENDING（평가대기）, PASS(통과), OUT(아웃) 으로 구분됩니다.")
    public BaseResponse<WeeklyStatusCommonResponse> setWeeklyStatus(
        @PathVariable Long studyMemberId,
        @PathVariable int week,
        @Valid @RequestBody AdminWeeklyStatusRequest request
    ) {
        WeeklyStatusCommonResponse response = adminStudyAdviser.setWeeklyStatus(studyMemberId, week, request);
        return BaseResponse.onSuccess(response);
    }

    @GetMapping("/study-members/{studyMemberId}/workbook")
    @Operation(summary = "특정 스터디원의 주차별 워크북 상세 조회 API", description = "운영진이 특정 스터디원의 주차별 워크북 내용과 답변 현황, 통과 여부를 상세 조회합니다.")
    public BaseResponse<MemberWorkbookResponse> getMemberWorkbook(
        @PathVariable Long studyMemberId,
        @RequestParam int week
    ) {
        MemberWorkbookResponse response = adminStudyAdviser.getMemberWorkbook(studyMemberId, week);
        return BaseResponse.onSuccess(response);
    }

    @GetMapping("/members/search")
    @Operation(summary = "스터디원 추가를 위한 멤버 조회 API", description = "닉네임/이름으로 멤버를 검색하고, 권한에 따라 필터링하여 조회합니다.")
    public BaseResponse<AdminMemberSearchResponse> searchMembers(
        @CurrentMember Member adminMember,
        @RequestParam(required = false) String keyword,
        @PageableDefault(size = 5) Pageable pageable // 정렬은 QueryDSL에서 직접 처리
    ) {
        Page<MemberSearchInfo> memberPage = adminMemberAdviser.searchMembers(adminMember, keyword, pageable);
        return BaseResponse.onSuccess(AdminMemberSearchResponse.from(memberPage));
    }
}
