package umc.product.domain.event.repository.jpa.form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.event.entity.form.EventForm;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.util.Optional;

public interface EventFormRepository extends JpaRepository<EventForm, Long> {

    default EventForm getEventFormById(Long eventId){
        return findById(eventId)
                .orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_NOT_FOUND));

    }


    @Query("""
    SELECT ef FROM EventForm ef
    JOIN FETCH ef.questionList
    WHERE ef.event.id = :eventId
""")
    Optional<EventForm> findEventFormEventId(@Param("eventId") Long eventId);
}
