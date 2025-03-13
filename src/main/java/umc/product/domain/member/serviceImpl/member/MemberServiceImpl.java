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

    @Override
    public void existById(Long memberId) {
        if(!memberDslRepository.existById(memberId)) throw new RestApiException(MEMBER_NOT_FOUND);
    }

    public Member findById(Long memberId) throws UsernameNotFoundException {
        return memberDslRepository.findByIdNotFetchLoginInfo(memberId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));
    }

    @Override
    public Member findByIdNotFetchLoginInfo(Long memberId) {
        return memberDslRepository.findByIdNotFetchLoginInfo(memberId)
                .orElseThrow(() -> new RestApiException(MEMBER_NOT_FOUND));
    }

    @Transactional
    @Override
    public void modifyMyProfileAvatar(Member member, String avatarUrl) {
        memberDslRepository.updateAvatarImage(member, avatarUrl);
    }
}
