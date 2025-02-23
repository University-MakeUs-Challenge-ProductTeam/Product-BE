package umc.product.domain.suggestion.converter;

import org.springframework.stereotype.Component;
import umc.product.domain.suggestion.dto.response.SuggestionCommentIdResponse;
import umc.product.domain.suggestion.entity.SuggestionComment;

@Component
public class SuggestionCommentConverter {
    public SuggestionCommentIdResponse toSuggestionCommentIdResponse(SuggestionComment suggestionComment) {
        return SuggestionCommentIdResponse.builder()
                .suggestionCommentId(suggestionComment.getId())
                .build();
    }
}
