package umc.product.domain.event.repository.jpa.event;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventMember;
import umc.product.domain.member.entity.Member;

import java.util.Optional;

public interface EventMemberRepository extends JpaRepository<EventMember, Long> {
    Optional<EventMember> findByEventAndMember(Event event, Member member);
    Long countByEventAndIsReadTrue(Event event);
    Long countByEventAndIsCheckedTrue(Event event);
}
