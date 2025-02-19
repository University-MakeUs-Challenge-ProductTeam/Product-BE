package umc.product.domain.member.service.admin;

import umc.product.domain.member.dto.request.admin.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.common.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.university.entity.University;

public interface AdminAuthService {
    Member signUp(Member member, String password, University university);
    // 자체 로그인
    MemberLoginResponse login(AdminLoginRequest request);
}
