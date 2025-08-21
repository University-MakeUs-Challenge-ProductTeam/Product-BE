package umc.product.domain.event.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventReview;
import umc.product.domain.member.entity.Member;

@Component
public class EventReviewMapper {

    public EventReview toEventReview(Event event, Member member, String content) {
        return EventReview.builder()
                .content(content)
                .event(event)
                .writer(member)
                .build();
    }
}
