package umc.product.domain.event.repository.jpa.form;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.event.entity.participation.EventFormAnswer;

public interface EventFormAnswerRepository extends JpaRepository<EventFormAnswer, Long> {
    @Modifying
    @Query("UPDATE EventFormAnswer efa SET efa.eventParticipation = null WHERE efa.eventParticipation.id = :participationEventId")
    void disconnectAnswersFromParticipation(@Param("participationEventId") Long participationEventId);
}
