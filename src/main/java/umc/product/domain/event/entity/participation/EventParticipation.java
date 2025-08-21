package umc.product.domain.event.entity.participation;

import jakarta.persistence.*;
import lombok.*;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventParticipation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ParticipationStatus participationStatus = ParticipationStatus.ABSENT; // 참석 현황(불참 기본값)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_mamber_id", nullable = false)
    private Member participationMember;

    public void updateParticipationStatus(ParticipationStatus participationStatus) {
        this.participationStatus = participationStatus;
    }
}
