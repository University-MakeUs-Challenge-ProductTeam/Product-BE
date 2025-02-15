package umc.product.domain.event.entity.participation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventFormResponse extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_form_id", nullable = false)
    private EventForm eventForm;  // 해당 응답이 속한 신청 폼

    @Column(nullable = false)
    private Long userId;  // 응답을 제출한 사용자 ID

    @OneToMany(mappedBy = "response")
    private List<EventFormAnswer> answers;  // 응답에 포함된 답변들

    // 역할 책임 분리를 위해 ParticipationEvent을 EventFormResponse에서 분리해서 일대일 관계로 설정
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_event_id", nullable = false)
    private ParticipationEvent participationEvent; // 해당 답변이 속한 참가 이벤트

    @Builder
    public EventFormResponse(EventForm eventForm, Long userId, List<EventFormAnswer> answers, ParticipationEvent participationEvent) {
        this.eventForm = eventForm;
        this.userId = userId;
        this.answers = answers;
        this.participationEvent = participationEvent;
    }
}

