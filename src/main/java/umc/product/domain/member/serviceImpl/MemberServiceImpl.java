package umc.product.domain.member.serviceImpl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;
import umc.product.domain.member.mapper.MemberInfoMapper;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.MemberRepository;
import umc.product.domain.member.service.MemberService;
import umc.product.domain.member.status.MemberErrorStatus;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.config.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;
    private final MemberInfoMapper memberInfoMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MemberIdResponse signUp(MemberSignUpRequest request) {
        Member member = memberMapper.toMember(request);
        MemberLoginInfo memberLoginInfo = memberInfoMapper.toMemberInfo(request.getClientId(), passwordEncoder.encode(request.getPassword()), member);
        member.setMemberLoginInfo(memberLoginInfo);
        return new MemberIdResponse(saveEntity(member).getId());
    }

    public Member findById(Long id) throws UsernameNotFoundException {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RestApiException(MemberErrorStatus.EMPTY_MEMBER));
    }

    // 회원 저장
    public Member saveEntity(Member member) {
        return memberRepository.save(member);
    }

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RestApiException(MemberErrorStatus.UNAUTHORIZED); // 로그인 안함
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof PrincipalDetails) {
            return ((PrincipalDetails) principal).getMember();
        }

        throw new RestApiException(MemberErrorStatus.AUTHENTICATION_FAILED); // 로그인 정보를 확인할 수 없음

    }
}
