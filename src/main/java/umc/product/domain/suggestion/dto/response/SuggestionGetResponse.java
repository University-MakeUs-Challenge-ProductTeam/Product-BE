package umc.product.domain.suggestion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.dto.query.SuggestionQueryDto;
import umc.product.domain.suggestion.entity.Suggestion;
import umc.product.domain.suggestion.entity.enums.SuggestionTarget;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SuggestionGetResponse {
    private int totalPage;
    private int page;
    private List<SuggestionQueryDto> suggestions;
}
