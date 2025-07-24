package umc.product.domain.event.service.member.form;

import umc.product.domain.event.entity.form.EventFormQuestion;

import java.util.List;

public interface EventFormService {
    List<EventFormQuestion> inquiryEventForm(Long eventId);
}
