package umc.product.domain.event.entity;

import jakarta.persistence.*;
import lombok.Getter;
import umc.product.domain.member.entity.Member;
import umc.product.global.common.base.BaseEntity;

import java.util.List;

@Entity
@Getter
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
}
