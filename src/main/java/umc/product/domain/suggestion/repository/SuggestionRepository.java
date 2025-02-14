package umc.product.domain.suggestion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import umc.product.domain.suggestion.dto.query.SuggestionQueryDto;
import umc.product.domain.suggestion.entity.Suggestion;

import java.util.Optional;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    Optional<Suggestion> findSuggestionById(Long suggestionId);

    @Query("select new umc.product.domain.suggestion.dto.query.SuggestionQueryDto(" +
            "s.id, s.title, s.completedStatus, s.createdAt, s.suggestionTarget," +
            "case when s.anonymityStatus = true then 'Anonymous' " +
            "     when s.anonymityStatus = false then s.member.name end, " +
            "case when s.anonymityStatus = true then 'Anonymous' " +
            "     when s.anonymityStatus = false then s.member.nikeName end," +
            "case when s.anonymityStatus = true then 'Anonymous' " +
            "     when s.anonymityStatus = false then s.member.avatarUrl end) " +
            "from Suggestion s " +
            "where s.deletedAt = null " +
            "order by s.createdAt desc ")
    Page<SuggestionQueryDto> findAllByOrderByCreatedAtDesc(Pageable pageable);


}
