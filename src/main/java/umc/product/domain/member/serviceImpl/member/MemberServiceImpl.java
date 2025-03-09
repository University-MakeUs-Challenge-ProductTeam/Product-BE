package umc.product.domain.member.serviceImpl.member;

import jakarta.transaction.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.repository.jpa.MemberJpaRepository;
import umc.product.domain.member.repository.querydsl.MemberDslRepository;
import umc.product.domain.member.service.member.MemberService;
import umc.product.domain.member.status.MemberErrorStatus;
import umc.product.domain.university.entity.University;
import umc.product.global.common.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static umc.product.domain.member.status.MemberErrorStatus.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberDslRepository memberDslRepository;
    private final MemberJpaRepository memberJpaRepository;

    public Member findById(Long id) throws UsernameNotFoundException {
        return memberDslRepository.findById(id)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));
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
        return memberDslRepository.findWaitingMemberByUniversity(university);
    }

    @Override
    public List<Member> findWaitingMember() {
        return memberDslRepository.findWaitingMember();
    }
}
