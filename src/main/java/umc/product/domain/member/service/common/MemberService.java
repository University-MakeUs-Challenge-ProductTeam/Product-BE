package umc.product.domain.member.service.common;

import umc.product.domain.member.dto.request.member.MemberSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberCode;

public interface MemberService {
    public Member toCommonMember(MemberSignUpRequest request, String avatarUrl);
    public Member findById(Long id);
    public Member saveEntity(Member member);
    public MemberCode verifyMemberCode(String code);
    public Member modifyMyProfileAvatar(Member member, String avatarUrl);
}
