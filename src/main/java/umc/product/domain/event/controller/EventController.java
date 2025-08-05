package umc.product.domain.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.event.adviser.EventAdviser;
import umc.product.domain.event.dto.request.form.EventFormAnswerRequest;
import umc.product.domain.event.dto.request.participation.ParticipationCancelRequest;
import umc.product.domain.event.dto.response.event.EventPagingResponse;
import umc.product.domain.event.dto.response.event.EventSummaryResponse;
import umc.product.domain.event.dto.response.form.EventFormResponse;
import umc.product.domain.event.dto.response.participation.ParticipationIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

import java.util.List;

@Tag(name = "챌린저용 행사 API", description = "챌린저용 행사 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventAdviser eventAdviser;

    @Operation(summary = "행사 조회 API", description = "최신순으로 행사 조회")
    @Parameters(value = {
            @Parameter(name = "page", description = "페이지 번호(0부터 시작)"),
            @Parameter(name = "size", description = "한 페이지 당 이벤트 개수"),
    })
    @GetMapping("/list")
    public BaseResponse<EventPagingResponse<EventSummaryResponse>> inquiryEvents(
            @RequestParam int page,
            @RequestParam int size
    ){
        return BaseResponse.onSuccess(eventAdviser.inquiryEvents(page, size));
    }

    @Operation(summary = "행사 검색 API", description = "제목, 내용 내의 키워드 검색")
    @Parameters(value = {
            @Parameter(name = "keyword", description = "검색할 키워드로 한글자 이상 입력"),
            @Parameter(name = "page", description = "페이지 번호(0부터 시작)"),
            @Parameter(name = "size", description = "한 페이지 당 이벤트 개수"),
    })
    @GetMapping("/search")
    public BaseResponse<EventPagingResponse<EventSummaryResponse>> inquiryEventsByKeyword(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ){
        return BaseResponse.onSuccess(eventAdviser.inquiryEventsByKeyword(keyword, page, size));
    }

    @Operation(summary = "행사 신청 폼 조회 API", description = "특정 행사의 신청 폼 조회")
    @Parameters(value = {
            @Parameter(name = "eventId", description = "조회하고 싶은 행사 ID"),
    })
    @GetMapping
    public BaseResponse<EventFormResponse> inquiryEventForm(
            @RequestParam(name = "eventId") Long eventId
    ){
        return BaseResponse.onSuccess(eventAdviser.inquiryEventForm(eventId));
    }

    @Operation(summary = "행사 신청 API", description = "챌린저용 행사 신청 API")
    @PostMapping("/apply")
    public BaseResponse<ParticipationIdResponse> applyEvent(
            @CurrentMember Member member,
            @Parameter(description = "질문 답변 json") @RequestPart List<EventFormAnswerRequest> answerList
    ){
        return BaseResponse.onSuccess(eventAdviser.applyEvent(member, answerList));
    }

    @Operation(summary = "행사 취소 API", description = "챌린저용 행사 취소 API")
    @PostMapping("/cancel")
    public BaseResponse<ParticipationIdResponse> cancelParticipation(
            @CurrentMember Member member,
            @RequestBody ParticipationCancelRequest request
    ){
        return BaseResponse.onSuccess(eventAdviser.cancelParticipation(member, request));
    }
}
