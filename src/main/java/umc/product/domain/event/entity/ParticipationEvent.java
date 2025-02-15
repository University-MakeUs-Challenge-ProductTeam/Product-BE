package umc.product.domain.event.entity;

import jakarta.persistence.*;
import lombok.Getter;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
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
}
