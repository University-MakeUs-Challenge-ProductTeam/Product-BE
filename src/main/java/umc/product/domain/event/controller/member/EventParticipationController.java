package umc.product.domain.event.controller.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.event.adviser.member.EventParticipationAdviser;
import umc.product.domain.event.dto.request.form.EventFormAnswerRequest;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

import java.util.List;

@Tag(name = "행사 참여 API", description = "행사 참여 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events/participations")
public class EventParticipationController {

    private final EventParticipationAdviser eventParticipationAdviser;

    @Operation(summary = "행사 신청 API", description = "챌린저용 행사 신청 API")
    @PostMapping
    public BaseResponse<ParticipationIdResponse> inquiryEventForm(
            @CurrentMember Member member,
            @Parameter(description = "질문 답변 json") @RequestPart List<EventFormAnswerRequest> answerList
    ){
        return BaseResponse.onSuccess(eventParticipationAdviser.applyEvent(member, answerList));
    }
}
