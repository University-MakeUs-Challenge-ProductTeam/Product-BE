package umc.product.domain.suggestion.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.dto.query.MySuggestionQueryDto;
import umc.product.domain.suggestion.dto.query.SuggestionQueryDto;
import umc.product.domain.suggestion.dto.request.SuggestionRequest;
import umc.product.domain.suggestion.dto.response.*;
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
}
