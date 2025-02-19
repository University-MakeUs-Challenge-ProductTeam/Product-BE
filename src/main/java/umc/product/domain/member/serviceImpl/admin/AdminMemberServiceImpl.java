package umc.product.domain.member.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.mapper.MemberInfoMapper;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.MemberCustomRepository;
import umc.product.domain.member.repository.MemberRepository;
import umc.product.domain.member.service.admin.AdminAuthService;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.global.common.enums.Status;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {
    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;

    private final MemberMapper memberMapper;
    @Override
    public AdminMemberListResponse findMembers(Member member, Pageable pageable, String semester, Role role, String part) {
        List<Member> members =  memberCustomRepository.findMembers(pageable,member, semester, role, part);
        return memberMapper.toAdminMemberListResponse(members);
    }

    @Transactional
    @Override
    public MemberIdResponse outChallenger(Member member) {
        member.setStatus(Status.OUT);
        return new MemberIdResponse(member.getId());
    }
}
