package umc.product.domain.suggestion.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.converter.SuggestionConverter;
import umc.product.domain.suggestion.dto.query.MySuggestionQueryDto;
import umc.product.domain.suggestion.dto.query.SuggestionQueryDto;
import umc.product.domain.suggestion.dto.request.SuggestionRequest;
import umc.product.domain.suggestion.dto.response.MySuggestionGetResponse;
import umc.product.domain.suggestion.dto.response.SuggestionDetailResponse;
import umc.product.domain.suggestion.dto.response.SuggestionGetResponse;
import umc.product.domain.suggestion.dto.response.SuggestionIdResponse;
import umc.product.domain.suggestion.entity.Suggestion;
import umc.product.domain.suggestion.entity.SuggestionComment;
import umc.product.domain.suggestion.service.SuggestionCommentService;
import umc.product.domain.suggestion.service.SuggestionService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SuggestionAdviser {
    private final SuggestionService suggestionService;
    private final SuggestionCommentService suggestionCommentService;

    private final SuggestionConverter suggestionConverter;

    public SuggestionIdResponse postSuggestion(Member member, SuggestionRequest suggestionRequest) {
        Suggestion suggestion = suggestionService.postSuggestion(member, suggestionRequest);
        return suggestionConverter.toSuggestionIdResponse(suggestion);
    }

    public SuggestionIdResponse patchSuggestion(Member member, Long suggestionId, SuggestionRequest suggestionRequest) {
        Suggestion suggestion = suggestionService.patchSuggestion(member, suggestionId, suggestionRequest);
        return suggestionConverter.toSuggestionIdResponse(suggestion);
    }

    public SuggestionIdResponse deleteSuggestion(Member member, Long suggestionId) {
        Suggestion suggestion = suggestionService.deleteSuggestion(member, suggestionId);
        return suggestionConverter.toSuggestionIdResponse(suggestion);
    }

    public SuggestionGetResponse getSuggestion(Member member, Pageable pageable) {
        Page<SuggestionQueryDto> suggestions = suggestionService.getSuggestion(member, pageable);
        return suggestionConverter.toSuggestionGetResponse(suggestions);
    }

    public MySuggestionGetResponse getMySuggestion(Member member, Pageable pageable) {
        Page<MySuggestionQueryDto> suggestions = suggestionService.getMySuggestion(member, pageable);
        return suggestionConverter.toMySuggestionGetResponse(suggestions);
    }

    public SuggestionDetailResponse getSuggestionDetail(Member member, Long suggestionId) {
        Suggestion suggestion = suggestionService.findSuggestionById(suggestionId);
        List<SuggestionComment> comments = suggestionCommentService.findSuggestionCommentsById(suggestionId);
        return suggestionConverter.toSuggestionDetailResponse(suggestion, comments, 0L);
    }

    public SuggestionIdResponse patchSuggestionStatus(Member member, Long suggestionId, boolean completedStatus) {
        Suggestion suggestion = suggestionService.patchSuggestionStatus(member,suggestionId,completedStatus);
        return suggestionConverter.toSuggestionIdResponse(suggestion);
    }
}
