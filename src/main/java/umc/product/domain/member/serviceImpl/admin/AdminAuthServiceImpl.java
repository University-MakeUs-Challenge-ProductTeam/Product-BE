package umc.product.domain.member.serviceImpl.admin;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;
import umc.product.domain.member.mapper.MemberInfoMapper;
import umc.product.domain.member.repository.MemberJpaRepository;
import umc.product.domain.member.service.admin.AdminAuthService;
import umc.product.domain.member.serviceImpl.member.MemberRefreshTokenServiceImpl;
import umc.product.domain.member.strategy.context.LoginContext;
import umc.product.domain.university.entity.University;

@Service
@AllArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {
    public final MemberRefreshTokenServiceImpl refreshTokenService;
    private final MemberJpaRepository memberJpaRepository;
    private final MemberInfoMapper memberInfoMapper;
    private final LoginContext loginContext;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Member signUp(Member member, String password, University university) {
        MemberLoginInfo memberLoginInfo = memberInfoMapper.toMemberInfo(member.getClientId(), passwordEncoder.encode(password), member);
        member.setMemberLoginInfo(memberLoginInfo);
        member.setUniversity(university);
        return memberJpaRepository.save(member);
    }

    // 자체 로그인을 수행하는 함수
    @Override
    @Transactional(readOnly = true)
    public MemberLoginResponse login(AdminLoginRequest request) {
        MemberLoginResponse response = loginContext.executeStrategy(request);
        refreshTokenService.saveRefreshToken(response.refreshToken(), response.memberId());
        return loginContext.executeStrategy(request);
    }
}
