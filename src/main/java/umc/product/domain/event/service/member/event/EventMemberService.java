package umc.product.domain.event.service.member.event;

import umc.product.domain.event.entity.event.EventMember;

public interface EventMemberService {
    EventMember markAsRead(Long eventId, Long memberId);
    EventMember markAsChecked(Long eventId, Long memberId);
}
