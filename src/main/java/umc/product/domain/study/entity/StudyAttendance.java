package umc.product.domain.study.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.study.entity.enums.Check;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.base.BaseEntity;
import umc.product.global.common.exception.RestApiException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyAttendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int week;

    @Enumerated(EnumType.STRING)
    private Check checkStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_member_id", nullable = false)
    private StudyMember studyMember;

    // 출석 상태 변경(기존 미입력 'YES' -> 참석, 'NO' -> 불참석)
    public void updateCheckStatus(Check attendance) {
        this.checkStatus = attendance;
    }
}
