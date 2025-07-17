package umc.product.domain.event.service.admin.event;

import umc.product.domain.event.dto.request.event.EventRegistrationSettingsRequest;
import umc.product.domain.event.dto.request.event.EventRegistrationSettingsUpdateRequest;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventRegistrationSettings;

public interface AdminEventRegistrationSettingService {
    EventRegistrationSettings createEventRegistrationSettings(EventRegistrationSettingsRequest request, Event event);
    EventRegistrationSettings updateeEventRegistrationSettings(EventRegistrationSettingsUpdateRequest request, Event event);

}
