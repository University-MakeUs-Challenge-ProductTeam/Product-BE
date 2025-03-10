package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.code.AdminCreateCodeResponse;
import umc.product.domain.member.dto.response.admin.code.AdminVerifyCodeResponse;
import umc.product.domain.member.dto.response.admin.register.AdminRegisterListResponse;
import umc.product.domain.member.dto.response.member.code.MemberCodeVerifyResponse;
import umc.product.domain.member.entity.Member;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MemberCodeConverter {

    public AdminCreateCodeResponse toAdminCodeResponse(
            String code
    ){
        return AdminCreateCodeResponse.builder()
                .code(code)
                .build();
    }

    public AdminRegisterListResponse toMemberCodeResponse(
            Map<String, Member> codeMap
    ){
        List<AdminRegisterListResponse.AdminRegisterMemberResponse> memberCodeInfoList = toMemberCodeInfoResponse(codeMap);
        return AdminRegisterListResponse.builder()
                .memberCodeList(memberCodeInfoList)
                .build();
    }

    public AdminVerifyCodeResponse toAdminCodeVerifyResponse(
            String universityName
    ) {
        return AdminVerifyCodeResponse.builder()
                .university(universityName)
                .build();
    }

    public MemberCodeVerifyResponse toMemberCodeVerifyResponse(
            Member member
    ) {
        return MemberCodeVerifyResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .build();
    }

    private List<AdminRegisterListResponse.AdminRegisterMemberResponse> toMemberCodeInfoResponse(
            Map<String, Member> codeMap
    ) {
        return codeMap.entrySet().stream()
                .map(entry -> {
                    return AdminRegisterListResponse.AdminRegisterMemberResponse.builder()
                            .memberId(entry.getValue().getId())
                            .code(entry.getKey())
                            .name(entry.getValue().getName())
                            .nickName(entry.getValue().getNickName())
                            .build();
                }).collect(Collectors.toList());
    }
}

