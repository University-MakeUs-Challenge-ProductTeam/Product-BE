package umc.product.domain.noticeMember.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.Notice;
import umc.product.global.common.base.BaseEntity;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Table(name = "notice_member")
public class NoticeMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

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
