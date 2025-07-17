package umc.product.domain.event.repository.jpa.form;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.form.EventForm;

public interface EventFormRepository extends JpaRepository<EventForm, Long> {
}
