package umc.product.domain.event.service.admin.event;

import umc.product.domain.event.entity.event.Event;

public interface AdminEventRegistrationSettingService {
    EventRegistrationSettings createEventRegistrationSettings(EventRegistrationSettingsRequest request, Event event);
    EventRegistrationSettings updateeEventRegistrationSettings(EventRegistrationSettingsUpdateRequest request, Event event);

}
