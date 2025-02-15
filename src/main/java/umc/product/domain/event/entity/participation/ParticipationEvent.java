package umc.product.domain.event.entity.participation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipationEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_mamber_id", nullable = false)
    private Member participationMember;

    // 역할 책임 분리를 위해 ParticipationEvent을 EventFormAnswer에서 분리해서 일대일 관계로 설정
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "participationEvent", cascade = CascadeType.ALL)
    @JoinColumn(name = "event_form_response_id")
    private EventFormResponse response; // 해당 참가 이벤트에 대한 신청 폼 응답

    @Builder
    public ParticipationEvent(Event event, Member participationMember, EventFormResponse eventFormResponse) {
        this.event = event;
        this.participationMember = participationMember;
        this.response = eventFormResponse;
    }
}
