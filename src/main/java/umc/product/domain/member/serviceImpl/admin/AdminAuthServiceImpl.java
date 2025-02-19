package umc.product.domain.member.serviceImpl.admin;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;
import umc.product.domain.member.mapper.MemberInfoMapper;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.MemberRepository;
import umc.product.domain.member.service.admin.AdminAuthService;

@Service
@AllArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {
    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;
    private final MemberInfoMapper memberInfoMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberIdResponse signUp(AdminSignUpRequest request, String avatarUrl) {
        Member member = memberMapper.toAdminMember(request, avatarUrl);
        MemberLoginInfo memberLoginInfo = memberInfoMapper.toMemberInfo(request.getClientId(), passwordEncoder.encode(request.getPassword()), member);
        member.setMemberLoginInfo(memberLoginInfo);
        return new MemberIdResponse(saveEntity(member).getId());
    }
    public Member saveEntity(Member member) {
        return memberRepository.save(member);
    }
}
