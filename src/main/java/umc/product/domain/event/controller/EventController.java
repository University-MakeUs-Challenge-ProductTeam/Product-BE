package umc.product.domain.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.product.domain.event.adviser.EventAdviser;
import umc.product.domain.event.dto.response.event.EventDetailResponse;
import umc.product.domain.event.dto.response.event.EventPagingResponse;
import umc.product.domain.event.dto.response.event.EventReviewIdResponse;
import umc.product.domain.event.dto.response.event.EventSummaryResponse;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

@Tag(name = "챌린저용 행사 API", description = "챌린저용 행사 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventAdviser eventAdviser;

    @Operation(summary = "행사 목록 조회 API", description = "최신순으로 행사 조회")
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

    @Operation(summary = "행사 타입별 목록 조회 API", description = "(중앙/지부/학교)행사 타입별 조회")
    @Parameters(value = {
            @Parameter(name = "type", description = "행사 조회 타입(중앙/지부/학교)"),
            @Parameter(name = "page", description = "페이지 번호(0부터 시작)"),
            @Parameter(name = "size", description = "한 페이지 당 이벤트 개수"),
    })
    @GetMapping("/list/type")
    public BaseResponse<EventPagingResponse<EventSummaryResponse>> inquiryEventsByEventType(
            @RequestParam EventType type,
            @RequestParam int page,
            @RequestParam int size
    ){
        return BaseResponse.onSuccess(eventAdviser.inquiryEventsByEventType(type, page, size));
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

    @Operation(summary = "행사 상세 조회 API", description = "특정 이벤트 상세조회")
    @GetMapping("/{eventId}/detail")
    public BaseResponse<EventDetailResponse> inquiryEventDetail(
            @Parameter(description = "조회할 이벤트 id") @PathVariable("eventId") Long eventId
    ) {
        return BaseResponse.onSuccess(eventAdviser.inquiryEventDetail(eventId));
    }

    @Operation(summary = "행사 리뷰 생성 API", description = "행사 리뷰 생성")
    @PostMapping("/{eventId}/review")
    @Parameters(value = {
            @Parameter(name = "content", description = "리뷰 내용")
    })
    public BaseResponse<EventReviewIdResponse> createReview(
            @CurrentMember Member member,
            @Parameter(description = "이벤트 id") @PathVariable("eventId") Long eventId,
            @RequestParam(name = "content") String content
    ) {
        return BaseResponse.onSuccess(eventAdviser.createReview(eventId, member, content));
    }

    @Operation(summary = "행사 리뷰 수정 API", description = "작성자만 수정 가능")
    @PatchMapping("/{eventReviewId}")
    @Parameters(value = {
            @Parameter(name = "content", description = "리뷰 내용")
    })
    public BaseResponse<EventReviewIdResponse> updateReview(
            @CurrentMember Member member,
            @Parameter(description = "수정할 행사 id") @PathVariable("eventReviewId") Long eventReviewId,
            @RequestParam(name = "content") String content
    ) {
        return BaseResponse.onSuccess(eventAdviser.updateReview(member, eventReviewId, content));
    }

    @Operation(summary = "행사 댓글 삭제 API", description = "작성자만 삭제 가능")
    @DeleteMapping("/{eventReviewId}")
    public BaseResponse<EventReviewIdResponse> deleteEvent(
            @CurrentMember Member member,
            @Parameter(description = "삭제할 행사 id") @PathVariable Long eventReviewId
    ) {
        return BaseResponse.onSuccess(eventAdviser.deleteReview(member, eventReviewId));
    }

}
