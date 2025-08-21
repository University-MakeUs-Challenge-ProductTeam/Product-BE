package umc.product.domain.event.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.adviser.AdminEventAdviser;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.dto.request.event.EventUpdateRequest;
import umc.product.domain.event.dto.response.event.AdminEventDetailResponse;
import umc.product.domain.event.dto.response.event.AdminEventSummaryResponse;
import umc.product.domain.event.dto.response.event.EventIdResponse;
import umc.product.domain.event.dto.response.event.EventPagingResponse;
import umc.product.domain.event.dto.response.participation.AdminParticipationMemberResponse;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.dto.response.participation.ParticipationPagingResponse;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.event.entity.participation.ParticipationStatus;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

import java.util.List;

@Tag(name = "운영진용 행사 API", description = "운영진용 행사 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/web/admin/events")
public class AdminEventController {

    private final AdminEventAdviser adminEventAdviser;

    @Operation(summary = "행사 생성 API", description = "운영진만 등록 가능")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<EventIdResponse> createEvent(
            @CurrentMember Member member,
            @Parameter(description = "행사 이미지 파일들(없을 시 사용 x)") @RequestPart(value = "eventImages", required = false) List<MultipartFile> eventImages,
            @Parameter(description = "행사 등록 요청 json") @Valid @RequestPart EventRequest request
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.createEvent(member,eventImages, request));
    }

    @Operation(summary = "행사 수정 API", description = "작성자만 수정 가능")
    @PatchMapping(value = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<EventIdResponse> updateEvent(
            @CurrentMember Member member,
            @Parameter(description = "수정할 행사 id") @PathVariable Long eventId,
            @Parameter(description = "행사 이미지 파일들(없을 시 사용 x)") @RequestPart(value = "eventImages", required = false) List<MultipartFile> eventImages,
            @Parameter(description = "행사 등록 요청 json") @Valid @RequestPart EventUpdateRequest request
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.updateEvent(member, eventId, eventImages, request));
    }

    @Operation(summary = "행사 삭제 API", description = "작성자만 삭제 가능")
    @DeleteMapping(value = "/{eventId}")
    public BaseResponse<EventIdResponse> deleteEvent(
            @CurrentMember Member member,
            @Parameter(description = "삭제할 행사 id") @PathVariable Long eventId
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.deleteEvent(member, eventId));
    }

    @Operation(summary = "행사 목록 조회 API", description = "기수, 월, 중앙/지부/교내 필터 조회")
    @Parameters(value = {
            @Parameter(name = "month", description = "월"),
            @Parameter(name = "semester", description = "학기"),
            @Parameter(name = "eventType", description = "행사 주최 타입(중앙/지부/학교)"),
            @Parameter(name = "page", description = "페이지 번호(0부터 시작)"),
            @Parameter(name = "size", description = "한 페이지 당 이벤트 개수"),
    })
    @GetMapping("/list")
    public BaseResponse<EventPagingResponse<AdminEventSummaryResponse>> inquiryEventsByFilter(
            @RequestParam(name = "month") Integer month,
            @RequestParam(name = "semester") String semester,
            @RequestParam(name = "eventType") EventType eventType,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.inquiryEventsByFilter(
                month, semester, eventType, page, size
    ));
    }

    @Operation(summary = "행사 상세 조회 API")
    @GetMapping("/{eventId}/detail")
    public BaseResponse<AdminEventDetailResponse> inquiryEventDetail(
            @Parameter(description = "조회할 이벤트 id") @PathVariable("eventId") Long eventId
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.inquiryEventDetail(eventId));
    }

    @Operation(summary = "행사 참여 인원 조회 API", description = "운영진만 조회 가능")
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지 번호(0부터 시작)"),
            @Parameter(name = "size", description = "한 페이지 당 이벤트 개수"),
    })
    @GetMapping("/{eventId}")
    public BaseResponse<ParticipationPagingResponse<AdminParticipationMemberResponse>> inquiryParticipationMembers(
            @Parameter(description = "조회할 행사 id") @PathVariable Long eventId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.inquiryParticipationMembers(eventId, page, size));
    }

    @Operation(summary = "행사 참여 인원 제거 API", description = "운영진만 제거 가능")
    @PatchMapping("/{participationId}")
    public BaseResponse<ParticipationIdResponse> deleteParticipationEvent(
            @Parameter(description = "삭제할 행사 참여 id") @PathVariable Long participationId
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.deleteParticipationEvent(participationId));
    }

    @Operation(summary = "행사 출석 상태 수정 API", description = "운영진만 변경 가능")
    @Parameters(value = {
            @Parameter(name = "participationId", description = "수정할 행사 참여 아이디"),
            @Parameter(name = "status", description = "수정될 출석 상태"),
    })
    @PatchMapping("/{participationId}/status")
    public BaseResponse<ParticipationIdResponse> updateParticipationStatus(
            @RequestParam(name = "participationId") @PathVariable Long participationId,
            @RequestParam(name = "status") ParticipationStatus status
    
    ){
        return BaseResponse.onSuccess(adminEventAdviser.updateParticipationStatus(participationId, status));
    }
}
