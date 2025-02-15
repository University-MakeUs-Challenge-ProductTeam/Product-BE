package umc.product.domain.suggestion.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.suggestion.dto.query.SuggestionQueryDto;
import umc.product.domain.suggestion.dto.request.SuggestionRequest;
import umc.product.domain.suggestion.entity.Suggestion;
import umc.product.domain.suggestion.mapper.SuggestionMapper;
import umc.product.domain.suggestion.repository.SuggestionRepository;
import umc.product.domain.suggestion.service.SuggestionService;
import umc.product.global.common.exception.RestApiException;

import static umc.product.global.common.exception.code.status.SuggestionErrorStatus.SUGGESTION_NOT_AUTH;
import static umc.product.global.common.exception.code.status.SuggestionErrorStatus.SUGGESTION_NOT_EXIST;

@Service
@AllArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final SuggestionMapper suggestionMapper;

    @Override
    public Suggestion findSuggestionById(Long suggestionId) {
        return suggestionRepository.findById(suggestionId)
                .orElseThrow(()->new RestApiException(SUGGESTION_NOT_EXIST));
    }

    @Override
    public Suggestion postSuggestion(Member member, SuggestionRequest suggestionRequest) {
        Suggestion suggestion = suggestionMapper.toSuggestion(member, suggestionRequest);
        return suggestionRepository.save(suggestion);
    }

    @Transactional
    @Override
    public Suggestion patchSuggestion(Member member, Long suggestionId, SuggestionRequest suggestionRequest) {
        Suggestion suggestion = suggestionRepository.findSuggestionById(suggestionId)
                .orElseThrow(()->new RestApiException(SUGGESTION_NOT_EXIST));

        if (!suggestion.getMember().getId().equals(member.getId())) {
            throw new RestApiException(SUGGESTION_NOT_AUTH);
        }

        suggestion.updateSuggestion(suggestionRequest);
        return suggestion;
    }

    @Transactional
    @Override
    public Suggestion deleteSuggestion(Member member, Long suggestionId) {
        Suggestion suggestion = suggestionRepository.findSuggestionById(suggestionId)
                .orElseThrow(()->new RestApiException(SUGGESTION_NOT_EXIST));

        if (!suggestion.getMember().getId().equals(member.getId())||
                member.getRole().equals(Role.GUEST) ||
                member.getRole().equals(Role.CHALLENGER)) {
            throw new RestApiException(SUGGESTION_NOT_AUTH);
        }

        suggestion.delete();
        return suggestion;
    }

    @Override
    public Page<SuggestionQueryDto> getSuggestion(Member member, Pageable pageable) {
        return suggestionRepository.findAllByOrderByCreatedAtDesc(pageable);
    }


    @Transactional
    @Override
    public Suggestion patchSuggestionStatus(Member member, Long suggestionId, boolean completedStatus) {
        Suggestion suggestion = suggestionRepository.findSuggestionById(suggestionId)
                .orElseThrow(()->new RestApiException(SUGGESTION_NOT_EXIST));

        if (!suggestion.getMember().getId().equals(member.getId()) ||
                member.getRole().equals(Role.GUEST) ||
                member.getRole().equals(Role.CHALLENGER)) {
            throw new RestApiException(SUGGESTION_NOT_AUTH);
        }

        suggestion.updateSuggestionStatus(completedStatus);
        return suggestion;
    }
}
