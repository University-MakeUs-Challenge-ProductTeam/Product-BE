package umc.product.domain.member.mapper;

import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.request.member.MemberSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.common.enums.Status;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member toAdminMember(AdminSignUpRequest request, String avatarUrl){
        return Member.builder()
                .birth(request.getBirth())
                .email(request.getEmail())
                .avatarUrl(avatarUrl)
                .name(request.getName())
                .gender(request.getGender())
                .nickName(request.getNikeName())
                .clientId(request.getClientId())
                .loginType(LoginType.INTERNAL)
                .status(Status.ACTIVE)
                .role(Role.BRANCH_ADMIN)
                .build();
    }

    public Member toCommonMember(MemberSignUpRequest request, String avatarUrl){
        return Member.builder()
                .birth(request.getBirth())
                .email(request.getEmail())
                .avatarUrl(avatarUrl)
                .name(request.getName())
                .gender(request.getGender())
                .nickName(request.getNikeName())
                .clientId(request.getClientId())
                .loginType(request.getLoginType())
                .status(Status.ACTIVE)
                .build();
    }

    public Member toMember(final String clientId, LoginType loginType){
        return Member.builder()
                .clientId(clientId)
                .loginType(loginType)
                .build();
    }
}

