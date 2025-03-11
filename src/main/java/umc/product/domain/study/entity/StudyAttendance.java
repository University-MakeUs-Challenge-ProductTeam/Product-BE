package umc.product.domain.study.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.product.domain.study.entity.enums.Check;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyAttendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int week;

    @Enumerated(EnumType.STRING)
    private Check checkStatus; // 출석 체크 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_member_id", nullable = false)
    private StudyMember studyMember;

    // 출석 상태 변경(기존 미입력 'YES' -> 참석, 'NO' -> 불참석)
    public void updateCheckStatus(Check attendance) {
        this.checkStatus = attendance;
    }
}
