package umc.product.domain.member.service.admin;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

public interface AdminMemberService {
    public Member toAdminMember(AdminSignUpRequest request, String avatarUrl);
    public List<Member> findMembers(Member member, Pageable pageable, String semester, Role role, Part part);
    public void outChallenger(Member member);
    public List<Member> findMembersBySearchString(String searchString);
}
