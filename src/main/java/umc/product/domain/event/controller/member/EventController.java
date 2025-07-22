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
import umc.product.domain.event.adviser.member.EventAdviser;
import umc.product.domain.event.dto.response.event.EventPagingResponse;
import umc.product.domain.event.dto.response.event.EventSummaryResponse;
import umc.product.global.common.base.BaseResponse;

@Tag(name = "행사 API", description = "행사 관련 API")
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



}
