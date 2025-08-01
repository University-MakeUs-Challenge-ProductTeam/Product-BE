package umc.product.domain.event.entity.form;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.EventParticipation;
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

    @OneToMany(mappedBy = "eventForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventFormQuestion> questionList = new ArrayList<>();  // 폼에 포함된 문항들

    @OneToMany(mappedBy = "eventForm")
    private List<EventParticipation> participationEventList = new ArrayList<>();  // 폼에 대한 응답들

    // 역할 책임 분리를 위해 EventForm을 Event에서 분리해서 일대일 관계로 설정
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;  // 해당 폼이 속한 행사

    @Builder
    public EventForm(String formTitle, String description, List<EventFormQuestion> questionList, List<EventParticipation> participationEventList, Event event) {
        this.formTitle = formTitle;
        this.description = description;
        this.event = event;

        // `null` 방지: null이면 빈 리스트로 초기화
        this.questionList = (questionList != null) ? questionList : new ArrayList<>();
        this.participationEventList = (participationEventList != null) ? participationEventList : new ArrayList<>();
    }

    public void updateEventFormInfo(String title, String description) {
        this.formTitle = title;
        this.description = description;
    }

    public void updateQuestionList(List<EventFormQuestion> questions) {
        this.questionList.clear();
        this.questionList.addAll(questions);
    }
}
