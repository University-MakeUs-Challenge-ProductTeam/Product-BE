package umc.product.domain.checklist.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.product.domain.study.entity.StudyMember;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

    // checkStatus 업데이트 메서드
    public void updateCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }
}
