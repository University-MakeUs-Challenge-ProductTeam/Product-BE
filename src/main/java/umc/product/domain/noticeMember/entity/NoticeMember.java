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
public class NoticeMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeMember_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    @Column(nullable = false)
    private boolean isRead;

    @Column(nullable = false)
    private boolean isChecked;
}
