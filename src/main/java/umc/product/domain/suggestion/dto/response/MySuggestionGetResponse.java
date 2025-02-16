package umc.product.domain.suggestion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.suggestion.dto.query.MySuggestionQueryDto;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MySuggestionGetResponse {
    private int totalPage;
    private int page;
    private List<MySuggestionQueryDto> suggestions;
}
