package umc.product.domain.suggestion.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.dto.query.SuggestionQueryDto;
import umc.product.domain.suggestion.dto.request.SuggestionRequest;
import umc.product.domain.suggestion.entity.Suggestion;

public interface SuggestionService {
    public Suggestion findSuggestionById(Long suggestionId);
    public Suggestion postSuggestion(Member member,
                                               SuggestionRequest suggestionRequest);

    public Suggestion patchSuggestion(Member member,
                                                Long suggestionId,
                                                SuggestionRequest suggestionRequest);


    public Suggestion deleteSuggestion(Member member,
                                                 Long suggestionId);

    public Page<SuggestionQueryDto> getSuggestion(Member member,
                                                  Pageable pageable);


    public Suggestion patchSuggestionStatus(Member member,
                                                      Long suggestionId,
                                                      boolean completedStatus);
}
