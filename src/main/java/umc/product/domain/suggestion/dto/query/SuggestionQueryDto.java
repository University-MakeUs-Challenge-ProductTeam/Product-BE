package umc.product.domain.suggestion.dto.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.suggestion.entity.enums.SuggestionTarget;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SuggestionQueryDto {
    private Long suggestionId;
    private String title;
    private boolean completedStatus;
    private LocalDateTime createdAt;
    private SuggestionTarget suggestionTarget;
    private String memberName;
    private String memberNickName;
    private String memberAvatarUrl;
}
