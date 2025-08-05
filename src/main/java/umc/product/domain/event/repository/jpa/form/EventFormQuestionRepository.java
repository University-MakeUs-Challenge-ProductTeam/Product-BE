package umc.product.domain.event.repository.jpa.form;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.form.EventFormQuestion;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.global.common.exception.RestApiException;

public interface EventFormQuestionRepository extends JpaRepository<EventFormQuestion, Long> {

    default EventFormQuestion getEventFormQuestion(Long questionId) {
        return findById(questionId)
                .orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_NOT_FOUND));
    }
}
