package umc.product.domain.event.dto.response.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.event.entity.form.ResponseType;

@Getter
@Builder
public class EventFormQuestionResponse {
    @Schema(description = "행사 신청 폼 질문 ID", example = "1")
    private Long eventFormQuestionId;

    @Schema(description = "질문 내용", example = "자기소개를 적어주세요")
    private String questionTitle;

    @Schema(description = "문항 설명", example = "최대 500자 이내로 작성")
    private String questionContent;

    @Schema(description = "응답 형식 (TEXT: 글 형식으로 입력, FILE_UPLOAD: 파일 업로드)", example = "TEXT")
    private ResponseType responseType;

    @Schema(description = "질문 순서", example = "3")
    private Integer questionOrder;
}
