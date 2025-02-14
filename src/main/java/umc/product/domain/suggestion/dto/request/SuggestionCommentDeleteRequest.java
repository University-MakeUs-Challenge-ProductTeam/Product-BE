package umc.product.domain.suggestion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SuggestionCommentDeleteRequest {
    private Long suggestionCommentId;
}
