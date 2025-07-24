package umc.product.domain.event.dto.response.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EventFormResponse {
    @Schema(description = "행사 신청 폼 ID", example = "1")
    private Long eventFormId;

    @Schema(description = "행사 신청 폼 제목", example = "UMC 데모데이 행사 신청")
    private String formTitle;

    @Schema(description = "행사 신청 폼 설명", example = "UMC 데모데이 행사 신청 어쩌구 저쩌구")
    private String description;

    @Schema(description = "질문 리스트")
    private List<EventFormQuestionResponse> questions;
}
