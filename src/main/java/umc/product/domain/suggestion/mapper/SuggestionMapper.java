package umc.product.domain.suggestion.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.dto.query.SuggestionQueryDto;
import umc.product.domain.suggestion.dto.request.SuggestionRequest;
import umc.product.domain.suggestion.dto.response.SuggestionCommentsResponse;
import umc.product.domain.suggestion.dto.response.SuggestionDetailResponse;
import umc.product.domain.suggestion.dto.response.SuggestionGetResponse;
import umc.product.domain.suggestion.dto.response.SuggestionIdResponse;
import umc.product.domain.suggestion.entity.Suggestion;
import umc.product.domain.suggestion.entity.SuggestionComment;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SuggestionMapper {
    private final SuggestionCommentMapper suggestionCommentMapper;
    public Suggestion toSuggestion(Member member, SuggestionRequest suggestionRequest) {
        return Suggestion.builder()
                .title(suggestionRequest.getTitle())
                .content(suggestionRequest.getContent())
                .suggestionTarget(suggestionRequest.getSuggestionTarget())
                .anonymityStatus(suggestionRequest.isAnonymityStatus())
                .member(member)
                .build();
    }

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

    public SuggestionDetailResponse toSuggestionDetailResponse(Suggestion suggestion, List<SuggestionComment> comments, Long depth){
        List<SuggestionCommentsResponse> suggestionCommentsResponseList
                = suggestionCommentMapper.toPostDetailCommentResultDTO(comments, 0L);

        return SuggestionDetailResponse.builder()
                .suggestionId(suggestion.getId())
                .title(suggestion.getTitle())
                .content(suggestion.getContent())
                .completedStatus(suggestion.isCompletedStatus())
                .createdAt(suggestion.getCreatedAt())
                .suggestionTarget(suggestion.getSuggestionTarget())
                .memberName(suggestion.getMember().getName())
                .memberNickName(suggestion.getMember().getNikeName())
                .memberAvatarUrl(suggestion.getMember().getAvatarUrl())
                .comments(suggestionCommentsResponseList)
                .build();
    }
}
