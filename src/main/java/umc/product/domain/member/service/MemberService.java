package umc.product.domain.member.service;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

public interface MemberService {
    MemberIdResponse signUp(AdminSignUpRequest request);
    public Member findById(Long id);
    public Member saveEntity(Member member);
    public AdminMemberListResponse findMembers(Member member, Pageable pageable, String semester, Role role, String part);

}
