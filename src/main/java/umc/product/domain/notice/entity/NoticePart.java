package umc.product.domain.notice.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import umc.product.domain.member.entity.enums.Part;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@SQLRestriction("deleted_at is null")
// 공지에 연결된 여러 파트를 식별하기 위해 사용
public class NoticePart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 공지사항과 연결된 파트 정보를 저장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    @Column(nullable = false)
    private Notice notice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Part part;

    // 생성 메서드
    public static NoticePart of(Notice notice, Part part) {
        return NoticePart.builder()
                .notice(notice)
                .part(part)
                .build();
    }
}
