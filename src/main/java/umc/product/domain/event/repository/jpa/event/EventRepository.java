package umc.product.domain.event.repository.jpa.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.global.common.exception.RestApiException;

public interface EventRepository extends JpaRepository<Event, Long> {

    default Event getEvent(Long eventId) {
        return findById(eventId)
                .orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_NOT_FOUND));
    }

    Page<Event> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
