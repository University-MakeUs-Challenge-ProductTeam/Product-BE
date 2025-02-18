package umc.product.domain.event.entity.participation;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.form.EventFormQuestion;
import umc.product.global.common.base.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventFormAnswer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participation_event_id", nullable = false)
    private ParticipationEvent participationEvent;  // 해당 답변이 속한 응답

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private EventFormQuestion question;  // 해당 답변이 속한 문항

    // todo : 글자수 제한
    @Column(nullable = true)
    private String answerText;  // 텍스트 답변

    // todo : 글자수 제한
    @Column(nullable = true)
    private String filePath;  // 파일 업로드 답변 경로

    @Builder
    public EventFormAnswer(ParticipationEvent participationEvent, EventFormQuestion question, String answerText, String filePath) {
        this.participationEvent = participationEvent;
        this.question = question;
        this.answerText = answerText;
        this.filePath = filePath;
    }
}

