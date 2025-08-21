package umc.product.domain.event.repository.jpa.event;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.EventReview;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {
}
