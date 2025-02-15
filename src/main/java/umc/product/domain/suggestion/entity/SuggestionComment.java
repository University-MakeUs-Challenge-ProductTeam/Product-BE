package umc.product.domain.suggestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.dto.request.SuggestionCommentRequest;
import umc.product.domain.suggestion.entity.enums.SuggestionTarget;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuggestionComment extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Long depth;

    @Column(nullable = false)
    private Long bundleId;

    @Column(nullable = false, length = 200)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private SuggestionComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SuggestionComment> childComments = new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggestion_id")
    private Suggestion suggestion;

    public void updateSuggestionComment(SuggestionCommentRequest suggestionCommentRequest) {
        this.comment = suggestionCommentRequest.getComment();
    }
}
