package umc.product.domain.member.service;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.dto.request.MemberAdminSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

public interface MemberService {
    MemberIdResponse signUp(MemberAdminSignUpRequest request);
    public Member findById(Long id);
    public Member saveEntity(Member member);
    public List<MemberSearchResponse> findMembers(Member member, Pageable pageable, String semester, Role role, String part);

}
