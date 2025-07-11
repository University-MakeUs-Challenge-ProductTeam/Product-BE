package umc.product.domain.event.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.event.dto.response.EventIdResponse;
import umc.product.domain.event.entity.event.Event;

@Component
@RequiredArgsConstructor
public class EventConverter {

    public EventIdResponse toEventIdResponse(Event event) {
        return EventIdResponse.builder()
                .eventId(event.getId())
                .build();
    }
}
