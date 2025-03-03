package umc.product.domain.member.serviceImpl.admin;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.dto.request.admin.AdminLoginRequest;
import umc.product.domain.member.dto.response.member.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;
import umc.product.domain.member.mapper.MemberInfoMapper;
import umc.product.domain.member.repository.MemberRepository;
import umc.product.domain.member.service.admin.AdminAuthService;
import umc.product.domain.member.serviceImpl.member.MemberRefreshTokenServiceImpl;
import umc.product.domain.member.strategy.context.LoginContext;
import umc.product.domain.university.entity.University;

@Service
@AllArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {
    public final MemberRefreshTokenServiceImpl refreshTokenService;
    private final MemberRepository memberRepository;
    private final MemberInfoMapper memberInfoMapper;
    private final LoginContext loginContext;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Member signUp(Member member, String password, University university) {
        MemberLoginInfo memberLoginInfo = memberInfoMapper.toMemberInfo(member.getClientId(), passwordEncoder.encode(password), member);
        member.setMemberLoginInfo(memberLoginInfo);
        member.setUniversity(university);
        return memberRepository.save(member);
    }

    // 자체 로그인을 수행하는 함수
    @Override
    @Transactional(readOnly = true)
    public MemberLoginResponse login(AdminLoginRequest request) {
        // 로그인 수행
        MemberLoginResponse response = loginContext.executeStrategy(request);

        // 리프레쉬 토큰 저장
        refreshTokenService.saveRefreshToken(response.getRefreshToken(), response.getMemberId());

        return loginContext.executeStrategy(request);
    }
}
