package umc.product.domain.member.mapper;

import umc.product.domain.member.dto.request.admin.AdminSignUpRequest;
import umc.product.domain.member.dto.response.admin.AdminMemberListResponse;
import umc.product.domain.member.dto.response.common.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.dto.response.common.MemberLoginResponse;
import umc.product.global.config.security.jwt.TokenInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {
    public Member toMember(AdminSignUpRequest request){
        return Member.builder()
                .birth(request.getBirth())
                .email(request.getEmail())
                .avatarUrl(request.getAvatar_url())
                .name(request.getName())
                .gender(request.getGender())
                .nikeName(request.getNikeName())
                .role(request.getRole())
                .clientId(request.getClientId())
                .loginType(LoginType.INTERNAL)
                .status(request.getStatus())
                .build();
    }

    public Member toMember(final String clientId, LoginType loginType){
        return Member.builder()
                .clientId(clientId)
                .loginType(loginType)
                .build();
    }

    public MemberLoginResponse toLoginMemberResponse(final Member member, TokenInfo tokenInfo, boolean isServiceMember, Role role) {
        return MemberLoginResponse.builder()
                .memberId(member.getId())
                .accessToken(tokenInfo.accessToken())
                .refreshToken(tokenInfo.refreshToken())
                .isServiceMember(isServiceMember)
                .role(role)
                .build();
    }

    public AdminMemberListResponse toAdminMemberListResponse(List<Member> memberList) {
        List<MemberSearchResponse> memberSearchResponse = memberList.stream()
                .map(this::toSearchMemberResponse).collect(Collectors.toList());
        return AdminMemberListResponse.builder()
                .memberList(memberSearchResponse)
                .build();
    }

    public MemberSearchResponse toSearchMemberResponse(Member member) {
        return MemberSearchResponse.builder()
                .memberId(member.getId())
                .avatarUrl(member.getAvatarUrl())
                .name(member.getName())
                .nickName(member.getNikeName())
                .university(member.getName()) //수정해야함
                .role(member.getRole().getToKorean())
                .status(member.getStatus())
                .build();
    }
}

