package umc.product.domain.suggestion.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.suggestion.dto.request.SuggestionCommentRequest;
import umc.product.domain.suggestion.entity.Suggestion;
import umc.product.domain.suggestion.entity.SuggestionComment;
import umc.product.domain.suggestion.mapper.SuggestionCommentMapper;
import umc.product.domain.suggestion.repository.SuggestionCommentRepository;
import umc.product.domain.suggestion.service.SuggestionCommentService;
import umc.product.domain.suggestion.service.SuggestionService;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

import static umc.product.global.common.exception.code.status.SuggestionErrorStatus.SUGGESTION_COMMENT_NOT_EXIST;
import static umc.product.global.common.exception.code.status.SuggestionErrorStatus.SUGGESTION_NOT_AUTH;

@Service
@AllArgsConstructor
public class SuggestionCommentServiceImpl implements SuggestionCommentService {
    private final SuggestionService suggestionService;
    private final SuggestionCommentRepository suggestionCommentRepository;
    private final SuggestionCommentMapper suggestionCommentMapper;

    @Override
    public SuggestionComment findSuggestionComment(Long suggestionCommentId) {
        return suggestionCommentRepository.findSuggestionCommentById(suggestionCommentId)
                .orElseThrow(()-> new RestApiException(SUGGESTION_COMMENT_NOT_EXIST));
    }

    @Override
    public List<SuggestionComment> findSuggestionCommentsById(Long suggestionId) {
        return suggestionCommentRepository.findSuggestionCommentsById(suggestionId);
    }

    @Transactional
    @Override
    public SuggestionComment postSuggestionComment(Member member, Long suggestionId, Long suggestionCommentId, SuggestionCommentRequest suggestionCommentRequest) {
        Suggestion suggestion = suggestionService.findSuggestionById(suggestionId);
        Integer maxBundleId = suggestionCommentRepository.findMaxBundleId().orElse(null);
        SuggestionComment suggestionComment;
        if (suggestionCommentId == null) { //최초댓글
            suggestionComment = suggestionCommentMapper.toSuggestionComment(member,suggestion, maxBundleId, suggestionCommentRequest);
        }else {
            SuggestionComment parentComment = this.findSuggestionComment(suggestionCommentId);
            suggestionComment = suggestionCommentMapper.toSuggestionChildComment(member, suggestion,suggestionCommentRequest, parentComment);
        }

        return suggestionCommentRepository.save(suggestionComment);
    }

    @Transactional
    @Override
    public SuggestionComment patchSuggestionComment(SuggestionComment suggestionComment, SuggestionCommentRequest suggestionCommentRequest) {
        suggestionComment.updateSuggestionComment(suggestionCommentRequest);

        return suggestionComment;
    }

    @Transactional
    @Override
    public SuggestionComment deleteSuggestionComment(Member member, SuggestionComment suggestionComment) {
        if (!suggestionComment.getMember().getId().equals(member.getId())||
                member.getRole().equals(Role.GUEST) ||
                member.getRole().equals(Role.CHALLENGER)) {
            throw new RestApiException(SUGGESTION_NOT_AUTH);
        }
        suggestionComment.delete();
        return suggestionComment;
    }
}
