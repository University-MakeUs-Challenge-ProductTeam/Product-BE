package umc.product.domain.suggestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.suggestion.entity.SuggestionComment;

import java.util.List;
import java.util.Optional;

public interface SuggestionCommentRepository extends JpaRepository<SuggestionComment, Long> {

    @Query(value = "SELECT COALESCE(MAX(sc.bundleId), 0) FROM SuggestionComment sc")
    Optional<Integer> findMaxBundleId();

    Optional<SuggestionComment> findSuggestionCommentById(Long suggetionCommentId);

    @Query("select sc  " +
            "from SuggestionComment sc " +
            "where sc.suggestion.id = :suggestionId and sc.depth = 0 " +
            "order by sc.createdAt desc ")
    List<SuggestionComment> findSuggestionCommentsById(@Param("suggestionId") Long suggestionId);
}
