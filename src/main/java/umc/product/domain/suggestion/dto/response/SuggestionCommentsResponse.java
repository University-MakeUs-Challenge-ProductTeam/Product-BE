package umc.product.domain.suggestion.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class SuggestionCommentsResponse {
    private Long suggestionCommentId;
    private String comment;
    private LocalDateTime createdAt;

    private String memberName;
    private String memberNickName;
    private String memberAvatarUrl;

    private Long depth;
    private Long bundleId;
    private List<SuggestionCommentsResponse> comments;
}
