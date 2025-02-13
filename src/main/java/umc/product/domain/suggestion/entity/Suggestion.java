package umc.product.domain.suggestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import umc.product.domain.member.entity.Member;
import umc.product.domain.suggestion.entity.enums.SuggestionTarget;
import umc.product.global.common.base.BaseEntity;

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
}
