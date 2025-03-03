package umc.product.domain.checklist.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id", nullable = false)
    private Checklist checklist;

    @OneToMany(mappedBy = "checklistContent", cascade = CascadeType.ALL)
    private List<ChecklistMemberAnswer> checklistMemberAnswerList;
}
