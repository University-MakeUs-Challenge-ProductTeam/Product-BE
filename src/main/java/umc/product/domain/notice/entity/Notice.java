package umc.product.domain.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.enums.NoticeTarget;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.base.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeTarget target; // CENTRAL, BRANCH, UNIVERSITY

    @Column
    private String hashtags; // 쉼표로 구분된 해시태그

    @Column
    private String images; // 쉼표로 구분된 이미지 URL

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event; // 연결된 행사 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @Column(nullable = false)
    private LocalDateTime noticeDate; // 공지 날짜

    @Column
    private LocalDateTime checkDeadline; // 열람 체크 마감 기한
}
