package umc.product.domain.event.dto.request.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.form.ResponseType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventFormQuestionRequest {

    @Schema(description = "질문 제목", example = "자기소개를 작성해 주세요")
    @NotBlank(message = "질문 제목은 필수 입력값입니다.")
    private String questionTitle;

    @Schema(description = "질문 설명", example = "최대 500자 이내로 작성해주세요")
    private String questionContent;

    @Schema(description = "응답 타입 (TEXT, FILE_UPLOAD)", example = "TEXT")
    private ResponseType responseType;

    @Schema(description = "질문 순서", example = "1")
    @NotBlank(message = "질문 순서는 필수 입력값입니다.")
    private Integer questionOrder;

}
