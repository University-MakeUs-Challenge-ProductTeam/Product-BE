package umc.product.domain.event.dto.request.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import umc.product.domain.event.entity.form.ResponseType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventFormAnswerRequest {

    @Schema(description = "문항 ID", example = "1")
    private Long questionId;

    @Schema(description = "응답 형식 (TEXT 또는 FILE_UPLOAD)", example = "TEXT")
    private ResponseType responseType;

    @Schema(description = "텍스트 답변 (TEXT 타입일 때만 입력)", example = "UMC에 지원하게 된 이유는...")
    private String answerText;

    @Setter
    @Schema(description = "파일 답변 (FILE_UPLOAD 타입일 때만 첨부)")
    private MultipartFile answerFile;

}
