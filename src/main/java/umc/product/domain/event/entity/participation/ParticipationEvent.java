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
public class ParticipationEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_form_id", nullable = false)
    private EventForm eventForm;  // 해당 참가가 속한 신청 폼

    @OneToMany(mappedBy = "participationEvent")
    private List<EventFormAnswer> answers = new ArrayList<>();   // 응답에 포함된 답변들

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_mamber_id", nullable = false)
    private Member participationMember;

    @Builder
    public ParticipationEvent(Event event, Member participationMember, EventForm eventForm, List<EventFormAnswer> answers) {
        this.event = event;
        this.participationMember = participationMember;
        this.eventForm = eventForm;
        this.answers = answers;
    }
}
