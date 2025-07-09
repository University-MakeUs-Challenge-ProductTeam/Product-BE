package umc.product.domain.notice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.enums.NoticeTarget;
import umc.product.domain.noticeMember.entity.NoticeMember;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.base.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // 공지에 해당하는 기수
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeSemester> noticeSemesters = new ArrayList<>();

    // 공지에 해당하는 파트
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticePart> noticeParts = new ArrayList<>();

    // 공지 멤버
    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoticeMember> noticeMembers = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime noticeDate; // 공지 날짜

    @Column
    private LocalDateTime checkDeadline; // 열람 체크 마감 기한
}
