package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.code.AdminVerifyCodeResponse;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterMemberResponse;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterResponse;
import umc.product.domain.member.dto.response.member.code.MemberCodeVerifyResponse;
import umc.product.domain.member.entity.Member;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MemberCodeConverter {

    public umc.product.domain.member.dto.response.admin.code.AdminCreateCodeResponse toAdminCodeResponse(String code){
        return umc.product.domain.member.dto.response.admin.code.AdminCreateCodeResponse.builder()
                .code(code)
                .build();
    }

    public AdminRegisterResponse toMemberCodeResponse(Map<String, Member> codeMap){
        List<AdminRegisterMemberResponse> memberCodeInfoList = toMemberCodeInfoResponse(codeMap);
        return AdminRegisterResponse.builder()
                .memberCodeList(memberCodeInfoList)
                .build();
    }

    public AdminVerifyCodeResponse toAdminCodeVerifyResponse(String universityName) {
        return AdminVerifyCodeResponse.builder()
                .university(universityName)
                .build();
    }

    public MemberCodeVerifyResponse toMemberCodeVerifyResponse(Member member) {
        return MemberCodeVerifyResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .build();
    }

    private List<AdminRegisterMemberResponse> toMemberCodeInfoResponse(Map<String, Member> codeMap) {
        return codeMap.entrySet().stream()
                .map(entry -> {
                    return AdminRegisterMemberResponse.builder()
                            .memberId(entry.getValue().getId())
                            .code(entry.getKey())
                            .name(entry.getValue().getName())
                            .nickName(entry.getValue().getNickName())
                            .build();
                }).collect(Collectors.toList());
    }
}

