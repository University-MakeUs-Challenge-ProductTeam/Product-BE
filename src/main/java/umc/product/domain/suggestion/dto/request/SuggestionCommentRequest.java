package umc.product.domain.suggestion.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import umc.product.domain.suggestion.entity.enums.SuggestionTarget;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionCommentRequest {
    @NotNull(message = "Content cannot be null")
    private String comment;
}
