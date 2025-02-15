package umc.product.domain.event.entity.form;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.participation.EventFormAnswer;
import umc.product.global.common.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventFormQuestion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo : 글자수 제한
    @Column(nullable = false)
    private String questionTitle;  // 문항 제목 (예: "자기소개를 적어주세요")

    // todo : 글자수 제한
    private String questionContent;  // 문항 설명 (예: "최대 500자 이내로 작성")

    @Enumerated(EnumType.STRING)
    private ResponseType responseType;  // 문항 유형 (TEXT, FILE_UPLOAD, RADIO, CHECKBOX 등)

    @Column(nullable = true)
    private Integer questionOrder;  // 문항 순서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_form_id", nullable = false)
    private EventForm eventForm;  // 해당 문항이 속한 신청 폼

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<EventFormAnswer> answers = new ArrayList<>();  // 문항에 대한 답변들

    @Builder
    public EventFormQuestion(String questionTitle, String questionContent, ResponseType responseType, Integer questionOrder, EventForm eventForm) {
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
        this.responseType = responseType;
        this.questionOrder = questionOrder;
        this.eventForm = eventForm;
    }
}
