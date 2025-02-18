package umc.product.domain.event.entity.form;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.ParticipationEvent;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventForm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo : 글자수 제한
    @Column(nullable = false)
    private String formTitle;  // 신청 폼 제목 (예: "UMC 행사 신청서")

    // todo : 글자수 제한
    private String description;  // 폼 설명

    @OneToMany(mappedBy = "eventForm")
    private List<EventFormQuestion> questions = new ArrayList<>();  // 폼에 포함된 문항들

    @OneToMany(mappedBy = "eventForm")
    private List<ParticipationEvent> participationEvents = new ArrayList<>();  // 폼에 대한 응답들

    // 역할 책임 분리를 위해 EventForm을 Event에서 분리해서 일대일 관계로 설정
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;  // 해당 폼이 속한 행사

    @Builder
    public EventForm(String formTitle, String description, List<EventFormQuestion> questions, List<ParticipationEvent> participationEvents, Event event) {
        this.formTitle = formTitle;
        this.description = description;
        this.questions = questions;
        this.participationEvents = participationEvents;
        this.event = event;
    }
}
