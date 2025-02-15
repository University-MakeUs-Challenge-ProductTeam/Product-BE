package umc.product.domain.event.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo : 글자수 제한
    private String title;

    // todo : 글자수 제한
    private String content;

    // todo : 글자수 제한
    private EventType eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<ParticipationEvent> participationEvents;

    // 역할 책임 분리를 위해 EventForm을 Event에서 분리해서 일대일 관계로 설정
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    @JoinColumn(name = "event_form_id")
    private EventForm eventForm; // 해당 행사에 대한 신청 폼

    @Builder
    public Event(String title, String content, EventType eventType, Member writer, List<ParticipationEvent> participationEvents, EventForm eventForm) {
        this.title = title;
        this.content = content;
        this.eventType = eventType;
        this.writer = writer;
        this.participationEvents = participationEvents;
        this.eventForm = eventForm;
    }
}
