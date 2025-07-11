package umc.product.domain.event.controller.admin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.adviser.admin.AdminEventAdviser;
import umc.product.domain.event.dto.request.EventRequest;
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
}
