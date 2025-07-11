package umc.product.domain.event.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventImage;

@Component
public class EventImageMapper {

    public EventImage toEventImage(Event event, String url) {
        return EventImage.builder()
                .event(event)
                .url(url)
                .build();
    }
}
