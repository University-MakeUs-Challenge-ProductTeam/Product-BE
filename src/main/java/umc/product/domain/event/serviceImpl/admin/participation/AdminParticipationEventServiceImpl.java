package umc.product.domain.event.serviceImpl.admin.participation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.repository.jpa.participation.ParticipationEventRepository;
import umc.product.domain.event.service.admin.participation.AdminParticipationEventService;

@Service
@RequiredArgsConstructor
public class AdminParticipationEventServiceImpl implements AdminParticipationEventService {

    private final ParticipationEventRepository participationEventRepository;

    @Override
    public int countByEvent(Event event) {
        return participationEventRepository.countByEvent(event);
    }

}
