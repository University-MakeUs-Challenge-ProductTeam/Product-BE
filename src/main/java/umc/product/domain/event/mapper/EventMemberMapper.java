package umc.product.domain.event.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventMember;
import umc.product.domain.member.entity.Member;

@Component
public class EventMemberMapper {

    public EventMember toEventMember(Event event, Member member){
        return EventMember.builder()
                .event(event)
                .member(member)
                .isRead(false)
                .isChecked(false)
                .build();
    }
}
