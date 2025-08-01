package umc.product.domain.event.entity.participation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventParticipation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ParticipationStatus participationStatus = ParticipationStatus.ABSENT; // 참석 현황(불참 기본값)

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cancel_reason_id")
    private CancelReason cancelReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_form_id", nullable = false)
    private EventForm eventForm;  // 해당 참가가 속한 신청 폼

    @OneToMany(mappedBy = "eventParticipation")
    private List<EventFormAnswer> answerList = new ArrayList<>();   // 응답에 포함된 답변들

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_mamber_id", nullable = false)
    private Member participationMember;

    @Builder
    public EventParticipation(Event event, ParticipationStatus participationStatus, Member participationMember, EventForm eventForm, List<EventFormAnswer> answerList) {
        this.event = event;
        this.participationStatus = participationStatus;
        this.participationMember = participationMember;
        this.eventForm = eventForm;

        //널 방지
        this.answerList = (answerList != null) ? answerList : new ArrayList<>();
    }

    public void updateParticipationStatus(ParticipationStatus participationStatus) {
        this.participationStatus = participationStatus;
    }

    public void cancel(CancelReason reason) {
        this.participationStatus = ParticipationStatus.CANCELED;
        this.cancelReason = reason;
    }

}
