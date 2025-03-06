package umc.product.domain.suggestion.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.suggestion.dto.query.MySuggestionQueryDto;
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
            "     when s.anonymityStatus = false then s.member.nickName end," +
            "case when s.anonymityStatus = true then 'Anonymous' " +
            "     when s.anonymityStatus = false then s.member.avatarUrl end) " +
            "from Suggestion s " +
            "where s.deletedAt is null " +
            "order by s.createdAt desc ")
    Page<SuggestionQueryDto> findSuggestion(Pageable pageable);

    @Query("select new umc.product.domain.suggestion.dto.query.MySuggestionQueryDto(" +
            "s.id, s.title, s.completedStatus, s.createdAt, s.suggestionTarget)" +
            "from Suggestion s " +
            "where s.deletedAt is null and s.member.id = :memberId " +
            "order by s.createdAt desc ")
    Page<MySuggestionQueryDto> findMySuggestion(@Param("memberId") Long memberId, Pageable pageable);


}
