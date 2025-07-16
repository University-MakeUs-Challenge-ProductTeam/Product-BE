package umc.product.domain.event.service.admin;

import umc.product.domain.event.dto.request.form.EventFormRequest;
import umc.product.domain.event.dto.request.form.EventFormUpdateRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.form.EventForm;

import java.util.List;

public interface AdminEventFormService {
    EventForm createForm(Event event, EventFormRequest request);
    EventForm updateForm(Event event, EventFormUpdateRequest request);
}
