package umc.product.domain.event.repository.jpa.form;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.form.EventFormQuestion;

public interface EventFormQuestionRepository extends JpaRepository<EventFormQuestion, Long> {
}
