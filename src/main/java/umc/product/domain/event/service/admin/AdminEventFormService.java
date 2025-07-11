package umc.product.domain.event.service.admin;

import umc.product.domain.event.dto.request.form.EventFormRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.form.EventForm;

public interface AdminEventFormService {
    EventForm createForm(Event event, EventFormRequest request);
}
