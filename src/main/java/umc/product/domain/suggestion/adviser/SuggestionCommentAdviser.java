package umc.product.domain.suggestion.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.converter.SuggestionCommentConverter;
import umc.product.domain.suggestion.dto.request.SuggestionCommentRequest;
import umc.product.domain.suggestion.dto.response.SuggestionCommentIdResponse;
import umc.product.domain.suggestion.entity.SuggestionComment;
import umc.product.domain.suggestion.service.SuggestionCommentService;

@Component
@RequiredArgsConstructor
public class SuggestionCommentAdviser {
    private final SuggestionCommentService suggestionCommentService;

    private final SuggestionCommentConverter suggestionCommentConverter;

    public SuggestionCommentIdResponse postSuggestionComment(Member member, Long suggestionId, Long suggestionCommentId, SuggestionCommentRequest suggestionCommentRequest) {
        SuggestionComment suggestionComment = suggestionCommentService.postSuggestionComment(member, suggestionId, suggestionCommentId, suggestionCommentRequest);
        return suggestionCommentConverter.toSuggestionCommentIdResponse(suggestionComment);
    }

    public SuggestionCommentIdResponse patchSuggestionComment(Member member, Long suggestionCommentId, SuggestionCommentRequest suggestionCommentRequest) {
        SuggestionComment suggestionComment = suggestionCommentService.findSuggestionComment(suggestionCommentId);
        suggestionComment = suggestionCommentService.patchSuggestionComment(suggestionComment, suggestionCommentRequest);

        return suggestionCommentConverter.toSuggestionCommentIdResponse(suggestionComment);
    }

    public SuggestionCommentIdResponse deleteSuggestionComment(Member member, Long suggestionCommentId) {
        SuggestionComment suggestionComment = suggestionCommentService.findSuggestionComment(suggestionCommentId);
        suggestionComment = suggestionCommentService.deleteSuggestionComment(member, suggestionComment);

        return suggestionCommentConverter.toSuggestionCommentIdResponse(suggestionComment);
    }
}
