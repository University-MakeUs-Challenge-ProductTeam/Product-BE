package umc.product.domain.member.service.admin;

import umc.product.domain.member.dto.request.admin.auth.AdminLoginRequest;
import umc.product.domain.member.dto.request.admin.auth.AdminSignUpRequest;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.university.entity.University;

public interface AdminAuthService {
    Member signUp(AdminSignUpRequest request,
                  Member member,
                  University university,
                  String avatarUrl);
    // 자체 로그인
    MemberLoginResponse login(AdminLoginRequest request);
}
