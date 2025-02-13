package umc.product.domain.member.service;

import umc.product.domain.member.dto.request.MemberCodeRequest;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberCodeResponse;
import umc.product.domain.member.dto.response.MemberIdResponse;
import umc.product.domain.member.entity.Member;

public interface MemberAdminService {
    MemberCodeResponse generateCode(MemberCodeRequest request);
}
