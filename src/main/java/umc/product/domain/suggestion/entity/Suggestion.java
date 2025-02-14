package umc.product.domain.suggestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.dto.request.SuggestionRequest;
import umc.product.domain.suggestion.entity.enums.SuggestionTarget;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Suggestion extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SuggestionTarget suggestionTarget;

    @NotNull
    private boolean anonymityStatus;

    @NotNull
    private boolean completedStatus;

    @OneToMany(mappedBy = "suggestion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SuggestionComment> comments = new ArrayList<>();

    public void updateSuggestion(SuggestionRequest request) {
        this.anonymityStatus = request.isAnonymityStatus();
        this.suggestionTarget = request.getSuggestionTarget();
        this.content = request.getContent();
        this.title = request.getTitle();
    }

    public void updateSuggestionStatus(boolean completedStatus) {
        this.completedStatus = completedStatus;
    }
}
