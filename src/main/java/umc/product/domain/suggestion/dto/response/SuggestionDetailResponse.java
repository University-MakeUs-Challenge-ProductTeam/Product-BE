package umc.product.domain.suggestion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.suggestion.entity.enums.SuggestionTarget;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SuggestionDetailResponse {
    private Long suggestionId;
    private String title;
    private String content;
    private boolean completedStatus;
    private LocalDateTime createdAt;
    private SuggestionTarget suggestionTarget;
    private String memberName;
    private String memberNickName;
    private String memberAvatarUrl;

    private List<SuggestionCommentsResponse> comments;
}
