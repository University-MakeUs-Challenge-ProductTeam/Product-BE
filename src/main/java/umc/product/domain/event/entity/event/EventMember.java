package umc.product.domain.event.entity.event;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import umc.product.domain.member.entity.Member;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
public class EventMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean isRead; // 열람 체크 여부

    @Column(nullable = false)
    private Boolean isChecked; // 수동 열람 확인 여부

    // 열람 상태 업데이트
    public void markAsRead() {
        this.isRead = true;
    }

    // 수동 열람 확인 업데이트
    public void markAsChecked() {
        this.isChecked = true;
    }
}
