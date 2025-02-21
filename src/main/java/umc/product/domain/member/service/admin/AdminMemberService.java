package umc.product.domain.member.service.admin;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

public interface AdminMemberService {
    public List<Member> findMembers(Member member, Pageable pageable, String semester, Role role, String part);
    public void outChallenger(Member member);
    public List<Member> findMembersBySearchString(String searchString);
}
