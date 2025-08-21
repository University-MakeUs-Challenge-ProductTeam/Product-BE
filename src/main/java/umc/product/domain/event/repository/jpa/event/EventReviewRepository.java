package umc.product.domain.event.repository.jpa.event;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.EventReview;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {

    default EventReview getEventReview(Long eventId) {
        return findById(eventId)
                .orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_REVIEW_NOT_FOUND));
    }

    List<EventReview> findAllByEventId(Long eventId);
}
