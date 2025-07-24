package umc.product.domain.event.controller.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.product.domain.event.adviser.member.EventFormAdviser;
import umc.product.domain.event.dto.response.form.EventFormResponse;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "일반 사용자 용 행사 API", description = "행사 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events/form")
public class EventFormController {

    private final EventFormAdviser eventFormAdviser;

    @Operation(summary = "행사 신청 폼 API", description = "특정 행사의 신청 폼 조회")
    @Parameters(value = {
            @Parameter(name = "eventId", description = "조회하고 싶은 행사 ID"),
    })
    @GetMapping
    public BaseResponse<EventFormResponse> inquiryEventsByKeyword(
            @RequestParam(name = "eventId") Long eventId
    ){
        return BaseResponse.onSuccess(eventFormAdviser.inquiryEventForm(eventId));
    }
}
