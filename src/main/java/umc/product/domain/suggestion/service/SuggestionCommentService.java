package umc.product.domain.suggestion.service;

import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.dto.query.SuggestionCommentQueryDto;
import umc.product.domain.suggestion.dto.request.SuggestionCommentRequest;
import umc.product.domain.suggestion.dto.response.SuggestionCommentIdResponse;
import umc.product.domain.suggestion.entity.SuggestionComment;

import java.util.List;

public interface SuggestionCommentService {
    public SuggestionComment findSuggestionComment(Long suggestionCommentId);

    public List<SuggestionComment> findSuggestionCommentsById(Long suggestionId);
    public SuggestionComment postSuggestionComment(Member member,
                                                             Long suggestionId,
                                                             Long suggestionCommentId,
                                                             SuggestionCommentRequest suggestionCommentRequest);

    public SuggestionComment patchSuggestionComment(SuggestionComment suggestionComment,
                                                    SuggestionCommentRequest suggestionCommentRequest);

    public SuggestionComment deleteSuggestionComment(SuggestionComment suggestionComment);
}
