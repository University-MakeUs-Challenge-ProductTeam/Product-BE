package umc.product.domain.member.service.admin;

import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberIdResponse;

public interface AdminAuthService {
    MemberIdResponse signUp(AdminSignUpRequest request, String avatarUrl);
}
