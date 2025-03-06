package umc.product.domain.member.serviceImpl.member;

import jakarta.transaction.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.repository.jpa.MemberJpaRepository;
import umc.product.domain.member.repository.querydsl.MemberRepository;
import umc.product.domain.member.service.member.MemberService;
import umc.product.domain.member.status.MemberErrorStatus;
import umc.product.domain.university.entity.University;
import umc.product.global.common.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberJpaRepository memberJpaRepository;

    public Member findById(Long id) throws UsernameNotFoundException {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new RestApiException(MemberErrorStatus.EMPTY_MEMBER));
    }

    // 회원 저장
    public Member saveEntity(Member member) {
        return memberJpaRepository.save(member);
    }

    @Transactional
    @Override
    public Member modifyMyProfileAvatar(Member member, String avatarUrl) {
        member.setAvatarUrl(avatarUrl);
        return memberJpaRepository.save(member);
    }

    @Override
    public List<Member> findWaitingMemberByUniversity(University university) {
        return memberRepository.findWaitingMemberByUniversity(university);
    }

    @Override
    public List<Member> findWaitingMember() {
        return memberRepository.findWaitingMember();
    }
}
