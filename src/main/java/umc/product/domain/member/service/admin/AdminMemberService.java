package umc.product.domain.member.service.admin;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;

public interface AdminMemberService {
    public AdminMemberListResponse findMembers(Member member, Pageable pageable, String semester, Role role, String part);

    public MemberIdResponse outChallenger(Member member);
}
