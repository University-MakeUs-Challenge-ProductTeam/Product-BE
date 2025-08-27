package umc.product.domain.event.serviceImpl.member.event;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.event.entity.event.Event;
import umc.product.domain.event.entity.event.EventMember;
import umc.product.domain.event.mapper.EventMemberMapper;
import umc.product.domain.event.repository.jpa.event.EventMemberRepository;
import umc.product.domain.event.repository.jpa.event.EventRepository;
import umc.product.domain.event.service.member.event.EventMemberService;
import umc.product.domain.event.status.EventErrorStatus;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.repository.jpa.MemberJpaRepository;
import umc.product.domain.member.status.MemberErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Service
@RequiredArgsConstructor
public class EventMemberServiceImpl implements EventMemberService {

    private final EventRepository eventRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final EventMemberRepository eventMemberRepository;
    private final EventMemberMapper eventMemberMapper;

    //행사 열람(열람시 자동 처리)
    @Override
    @Transactional
    public EventMember markAsRead(Long eventId, Long memberId){
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_NOT_FOUND));
        Member member = memberJpaRepository.findById(memberId).orElseThrow(()-> new RestApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        EventMember eventMember = eventMemberRepository.findByEventAndMember(event, member)
                .orElseGet(() -> eventMemberRepository.save(eventMemberMapper.toEventMember(event, member)));

        eventMember.markAsRead();

        return eventMember;
    }

    //행사 수동 확인
    @Override
    @Transactional
    public EventMember markAsChecked(Long eventId, Long memberId){
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_NOT_FOUND));
        Member member = memberJpaRepository.findById(memberId).orElseThrow(()-> new RestApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        EventMember eventMember = eventMemberRepository.findByEventAndMember(event, member)
                .orElseThrow(() -> new RestApiException(EventErrorStatus.EVENT_MEMBER_NOT_FOUND));

        eventMember.markAsChecked();

        if (!Boolean.TRUE.equals(eventMember.getIsRead())) {
            throw new IllegalStateException("행사 열람 후에만 확인 체크할 수 있습니다.");
        }

        return eventMember;
    }



}
