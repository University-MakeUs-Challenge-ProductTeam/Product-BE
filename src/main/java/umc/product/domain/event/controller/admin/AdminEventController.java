package umc.product.domain.event.controller.admin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.adviser.admin.AdminEventAdviser;
import umc.product.domain.event.dto.request.event.EventRequest;
import umc.product.domain.event.dto.request.event.EventUpdateRequest;
import umc.product.domain.event.dto.response.EventIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseResponse;
import umc.product.global.config.security.auth.CurrentMember;

import java.util.List;

@Tag(name = "운영진 용 행사 API", description = "행사 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class AdminEventController {

    private final AdminEventAdviser adminEventAdviser;

    @Operation(summary = "행사 생성 API", description = "운영진만 등록 가능")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<EventIdResponse> createEvent(
            @CurrentMember Member member,
            @Parameter(description = "행사 이미지 파일들(없을 시 사용 x)") @RequestPart List<MultipartFile> eventImages,
            @Parameter(description = "행사 등록 요청 json") @Valid @RequestPart EventRequest request
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.createEvent(member,eventImages, request));
    }

    @Operation(summary = "행사 수정 API", description = "작성자만 수정 가능")
    @PatchMapping(value = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<EventIdResponse> updateEvent(
            @CurrentMember Member member,
            @Parameter(description = "수정할 행사 id") @PathVariable Long eventId,
            @Parameter(description = "행사 이미지 파일들(없을 시 사용 x)") @RequestPart List<MultipartFile> eventImages,
            @Parameter(description = "행사 등록 요청 json") @Valid @RequestPart EventUpdateRequest request
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.updateEvent(member, eventId, eventImages, request));
    }

    @Operation(summary = "행사 삭제 API", description = "작성자만 삭제 가능")
    @DeleteMapping(value = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<EventIdResponse> deleteEvent(
            @CurrentMember Member member,
            @Parameter(description = "삭제할 행사 id") @PathVariable Long eventId
    ) {
        return BaseResponse.onSuccess(adminEventAdviser.deleteEvent(member, eventId));
    }
}
