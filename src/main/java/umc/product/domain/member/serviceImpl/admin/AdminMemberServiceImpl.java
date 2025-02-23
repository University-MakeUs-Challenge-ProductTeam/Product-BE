package umc.product.domain.member.serviceImpl.admin;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.MemberCustomRepository;
import umc.product.domain.member.service.admin.AdminMemberService;
import umc.product.global.common.enums.Status;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {
    private final MemberCustomRepository memberCustomRepository;

    private final MemberMapper memberMapper;

    @Override
    public Member toAdminMember(AdminSignUpRequest request, String avatarUrl) {
        return memberMapper.toAdminMember(request, avatarUrl);
    }

    @Override
    public List<Member> findMembers(Member member, Pageable pageable, String semester, Role role, Part part) {
        return memberCustomRepository.findMembers(pageable,member, semester, role, part);
    }

    @Transactional
    @Override
    public void outChallenger(Member member) {
        member.setStatus(Status.OUT);
    }

    @Override
    public List<Member> findMembersBySearchString(String searchString) {
        return memberCustomRepository.findMembersBySearchString(searchString);
    }
}
