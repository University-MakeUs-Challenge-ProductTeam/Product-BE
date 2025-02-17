package umc.product.domain.member.service;

import umc.product.domain.member.dto.request.MemberAdminSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberIdResponse;

public interface MemberAdminService {
    MemberIdResponse signUp(MemberAdminSignUpRequest request);
}
