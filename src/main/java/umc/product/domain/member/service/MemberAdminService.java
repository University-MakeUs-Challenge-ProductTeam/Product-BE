package umc.product.domain.member.service;

import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberIdResponse;

public interface MemberAdminService {
    MemberIdResponse signUp(AdminSignUpRequest request);
}
