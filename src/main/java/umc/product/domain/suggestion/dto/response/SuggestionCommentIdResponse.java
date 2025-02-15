package umc.product.domain.suggestion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SuggestionCommentIdResponse {
    private Long suggestionCommentId;
}
