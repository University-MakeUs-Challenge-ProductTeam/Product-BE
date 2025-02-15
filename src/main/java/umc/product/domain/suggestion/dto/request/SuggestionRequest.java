package umc.product.domain.suggestion.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.suggestion.entity.enums.SuggestionTarget;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionRequest {
    private String title;
    private String content;
    private boolean anonymityStatus;
    private SuggestionTarget suggestionTarget;
}
