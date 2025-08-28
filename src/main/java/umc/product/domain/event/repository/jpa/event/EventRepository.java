package umc.product.domain.event.repository.jpa.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.time.LocalDate;

public interface EventRepository extends JpaRepository<Event, Long> {

    default Event getEvent(Long eventId) {
        return findById(eventId)
                .orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_NOT_FOUND));
    }

    Page<Event> findAllByEventType(EventType eventType, Pageable pageable);
    Page<Event> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Event> findAllByEventDateBetween(LocalDate start, LocalDate end, Pageable pageable);


    @Query("""
        SELECT e FROM Event e
        WHERE LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(e.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Event> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
