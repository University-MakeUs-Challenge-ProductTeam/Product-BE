package umc.product.domain.event.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.participation.EventParticipation;
import umc.product.domain.member.entity.Member;

@Component
public class EventParticipationMapper {

    public EventParticipation toParticipationEvent(Member member, Event event) {
        return EventParticipation.builder()
                .participationMember(member)
                .event(event)
                .build();
    }

}
