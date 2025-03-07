package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.code.AdminVerifyCodeResponse;
import umc.product.domain.member.dto.request.admin.code.AdminCreateCodeResponse;
import umc.product.domain.member.dto.request.admin.code.AdminCreateCodeListResponse;
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

    public AdminCreateCodeListResponse toMemberCodeResponse(Map<String, Member> codeMap){
        List<AdminCreateCodeResponse> memberCodeInfoList = toMemberCodeInfoResponse(codeMap);
        return AdminCreateCodeListResponse.builder()
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

    private List<AdminCreateCodeResponse> toMemberCodeInfoResponse(Map<String, Member> codeMap) {
        return codeMap.entrySet().stream()
                .map(entry -> {
                    return AdminCreateCodeResponse.builder()
                            .memberId(entry.getValue().getId())
                            .code(entry.getKey())
                            .name(entry.getValue().getName())
                            .nickName(entry.getValue().getNickName())
                            .build();
                }).collect(Collectors.toList());
    }
}

