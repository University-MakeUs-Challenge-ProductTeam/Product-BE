package umc.product.domain.event.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.event.adviser.admin.AdminParticipationEventAdviser;
import umc.product.domain.event.dto.request.participation.ParticipationUpdateRequest;
import umc.product.domain.event.dto.response.participation.AdminParticipationMemberResponse;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.event.dto.response.participation.ParticipationPagingResponse;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "운영진 용 행사 참석 API", description = "행사 참석 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events/participations")
public class AdminParticipationEventController {
    private final AdminParticipationEventAdviser adminParticipationEventAdviser;

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
        return BaseResponse.onSuccess(adminParticipationEventAdviser.inquiryParticipationMembers(eventId, page, size));
    }

    @Operation(summary = "행사 참여 인원 제거 API", description = "운영진만 제거 가능")
    @PatchMapping("/{participationId}")
    public BaseResponse<ParticipationIdResponse> deleteParticipationEvent(
            @Parameter(description = "삭제할 행사 참여 id") @PathVariable Long participationId
    ) {
        return BaseResponse.onSuccess(adminParticipationEventAdviser.deleteParticipationEvent(participationId));
    }

    @Operation(summary = "행사 출석 상태 수정 API", description = "운영진만 변경 가능")
    @PatchMapping("/status")
    public BaseResponse<ParticipationIdResponse> updateParticipationStatus(
            @Valid @RequestBody ParticipationUpdateRequest request
    ){
        return BaseResponse.onSuccess(adminParticipationEventAdviser.updateParticipationStatus(request));
    }



}
