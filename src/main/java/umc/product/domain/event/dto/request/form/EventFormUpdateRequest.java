package umc.product.domain.event.dto.request.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventFormUpdateRequest {

    @Schema(description = "행사 신청 폼 제목", example = "UMC 행사 신청서")
    @NotBlank(message = "행사 신청 폼 제목은 필수 입력값입니다.")
    private String formTitle;

    @Schema(description = "행사 신청 폼 설명", example = "8기 데모데이 행사 신청 안내 문구.")
    @NotBlank(message = "행사 신청 폼 설명은 필수 입력값입니다.")
    private String description;

    @Schema(description = "행사 신청 폼 질문 목록")
    @Valid
    private List<EventFormQuestionRequest> questionList;
}
