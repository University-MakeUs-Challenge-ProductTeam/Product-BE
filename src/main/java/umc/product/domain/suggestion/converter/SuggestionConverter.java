package umc.product.domain.suggestion.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.suggestion.dto.query.MySuggestionQueryDto;
import umc.product.domain.suggestion.dto.query.SuggestionQueryDto;
import umc.product.domain.suggestion.dto.response.*;
import umc.product.domain.suggestion.entity.Suggestion;
import umc.product.domain.suggestion.entity.SuggestionComment;
import umc.product.domain.suggestion.mapper.SuggestionCommentMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SuggestionConverter {
    private final SuggestionCommentMapper suggestionCommentMapper;
    public SuggestionIdResponse toSuggestionIdResponse(Suggestion suggestion) {
        return SuggestionIdResponse.builder()
                .suggestionId(suggestion.getId())
                .build();
    }

    public SuggestionGetResponse toSuggestionGetResponse(Page<SuggestionQueryDto> suggestions) {
        return SuggestionGetResponse.builder()
                .totalPage(suggestions.getTotalPages())
                .page(suggestions.getNumber())
                .suggestions(suggestions.getContent())
                .build();
    }

    public MySuggestionGetResponse toMySuggestionGetResponse(Page<MySuggestionQueryDto> suggestions) {
        return MySuggestionGetResponse.builder()
                .totalPage(suggestions.getTotalPages())
                .page(suggestions.getNumber())
                .suggestions(suggestions.getContent())
                .build();
    }

    public SuggestionDetailResponse toSuggestionDetailResponse(Suggestion suggestion, List<SuggestionComment> comments, Long depth){
        List<SuggestionCommentsResponse> suggestionCommentsResponseList
                = suggestionCommentMapper.toSuggestionDetailComment(comments, 0L);

        return SuggestionDetailResponse.builder()
                .suggestionId(suggestion.getId())
                .title(suggestion.getTitle())
                .content(suggestion.getContent())
                .completedStatus(suggestion.isCompletedStatus())
                .createdAt(suggestion.getCreatedAt())
                .suggestionTarget(suggestion.getSuggestionTarget())
                .memberName(suggestion.getMember().getName())
                .memberNickName(suggestion.getMember().getNickName())
                .memberAvatarUrl(suggestion.getMember().getAvatarUrl())
                .comments(suggestionCommentsResponseList)
                .build();
    }
}
