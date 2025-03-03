package umc.product.domain.checklist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.study.entity.StudyMember;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChecklistMemberAnswer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean checkStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_content_id", nullable = false)
    private ChecklistContent checklistContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_member_id", nullable = false)
    private StudyMember studyMember;
}
