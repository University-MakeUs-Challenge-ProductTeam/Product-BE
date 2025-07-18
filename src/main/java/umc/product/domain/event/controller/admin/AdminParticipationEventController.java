package umc.product.domain.event.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.event.adviser.admin.AdminParticipationEventAdviser;
import umc.product.domain.event.dto.response.participation.AdminParticipationMemberResponse;
import umc.product.domain.event.dto.response.participation.ParticipationPagingResponse;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "운영진 용 행사 참석 API", description = "행사 참석 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events/participations")
public class AdminParticipationEventController {
    private final AdminParticipationEventAdviser adminParticipationEventAdviser;

    @Operation(summary = "행사 참여 인원 조회 API", description = "운영만 조회 가능")
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지 번호(0부터 시작)"),
            @Parameter(name = "size", description = "한 페이지 당 이벤트 개수"),
    })
    @GetMapping("/{eventId}")
    public BaseResponse<ParticipationPagingResponse<AdminParticipationMemberResponse>> inquiryParticipationMembers(
            @PathVariable Long eventId,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ) {
        return BaseResponse.onSuccess(adminParticipationEventAdviser.inquiryParticipationMembers(eventId, page, size));
    }
}
