package umc.product.domain.suggestion.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.suggestion.dto.query.SuggestionQueryDto;
import umc.product.domain.suggestion.dto.request.SuggestionRequest;
import umc.product.domain.suggestion.dto.response.SuggestionDetailResponse;
import umc.product.domain.suggestion.dto.response.SuggestionGetResponse;
import umc.product.domain.suggestion.dto.response.SuggestionIdResponse;
import umc.product.domain.suggestion.entity.Suggestion;
import umc.product.domain.suggestion.entity.SuggestionComment;
import umc.product.domain.suggestion.mapper.SuggestionMapper;
import umc.product.domain.suggestion.service.SuggestionCommentService;
import umc.product.domain.suggestion.service.SuggestionService;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

import static umc.product.global.common.exception.code.status.SuggestionErrorStatus.SUGGESTION_NOT_AUTH;
import static umc.product.global.common.exception.code.status.SuggestionErrorStatus.SUGGESTION_NOT_EXIST;

@Component
@RequiredArgsConstructor
public class SuggestionAdviser {
    private final SuggestionService suggestionService;
    private final SuggestionCommentService suggestionCommentService;

    private final SuggestionMapper suggestionMapper;

    public SuggestionIdResponse postSuggestion(Member member, SuggestionRequest suggestionRequest) {
        Suggestion suggestion = suggestionService.postSuggestion(member, suggestionRequest);
        return suggestionMapper.toSuggestionIdResponse(suggestion);
    }

    public SuggestionIdResponse patchSuggestion(Member member, Long suggestionId, SuggestionRequest suggestionRequest) {
        Suggestion suggestion = suggestionService.patchSuggestion(member, suggestionId, suggestionRequest);
        return suggestionMapper.toSuggestionIdResponse(suggestion);
    }

    public SuggestionIdResponse deleteSuggestion(Member member, Long suggestionId) {
        Suggestion suggestion = suggestionService.deleteSuggestion(member, suggestionId);
        return suggestionMapper.toSuggestionIdResponse(suggestion);
    }

    public SuggestionGetResponse getSuggestion(Member member, Pageable pageable) {
        Page<SuggestionQueryDto> suggestions = suggestionService.getSuggestion(member, pageable);
        return suggestionMapper.toSuggestionGetResponse(suggestions);
    }

    public SuggestionDetailResponse getSuggestionDetail(Member member, Long suggestionId) {
        Suggestion suggestion = suggestionService.findSuggestionById(suggestionId);
        List<SuggestionComment> comments = suggestionCommentService.findSuggestionCommentsById(suggestionId);
        return suggestionMapper.toSuggestionDetailResponse(suggestion, comments, 0L);
    }

    public SuggestionIdResponse patchSuggestionStatus(Member member, Long suggestionId, boolean completedStatus) {
        Suggestion suggestion = suggestionService.patchSuggestionStatus(member,suggestionId,completedStatus);
        return suggestionMapper.toSuggestionIdResponse(suggestion);
    }
}
