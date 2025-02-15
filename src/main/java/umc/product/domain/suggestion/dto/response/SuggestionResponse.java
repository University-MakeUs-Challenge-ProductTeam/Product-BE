package umc.product.domain.suggestion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.entity.enums.SuggestionTarget;

@Getter
@Builder
@AllArgsConstructor
public class SuggestionResponse {
    private Long suggestionId;
    private String title;
    private String content;
    private Member member;
    private SuggestionTarget suggestionTarget;
    private boolean anonymityStatus;
    private boolean completedStatus;
}
