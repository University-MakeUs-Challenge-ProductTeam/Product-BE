package umc.product.domain.member.service;

import umc.product.domain.member.dto.request.MemberCodeRequest;
import umc.product.domain.member.dto.request.MemberLoginRequest;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberGenerateTokenResponse;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.dto.response.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;
import umc.product.domain.member.entity.enums.LoginType;

public interface MemberService {
    MemberIdResponse signUp(MemberSignUpRequest request);
    public Member findById(Long id);
    public Member saveEntity(Member member);

}
