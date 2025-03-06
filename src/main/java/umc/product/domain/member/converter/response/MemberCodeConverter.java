package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.code.AdminCreateCodeResponse;
import umc.product.domain.member.dto.response.admin.code.AdminVerifyCodeResponse;
import umc.product.domain.member.dto.response.member.code.MemberCreateCodeResponse;
import umc.product.domain.member.dto.response.member.code.MemberCreateCodeListResponse;
import umc.product.domain.member.dto.response.member.code.MemberCodeVerifyResponse;
import umc.product.domain.member.entity.Member;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MemberCodeConverter {

    public AdminCreateCodeResponse toAdminCodeResponse(String code){
        return AdminCreateCodeResponse.builder()
                .code(code)
                .build();
    }

    public MemberCreateCodeListResponse toMemberCodeResponse(Map<String, Member> codeMap){
        List<MemberCreateCodeResponse> memberCodeInfoList = toMemberCodeInfoResponse(codeMap);
        return MemberCreateCodeListResponse.builder()
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

    private List<MemberCreateCodeResponse> toMemberCodeInfoResponse(Map<String, Member> codeMap) {
        return codeMap.entrySet().stream()
                .map(entry -> {
                    return MemberCreateCodeResponse.builder()
                            .memberId(entry.getValue().getId())
                            .code(entry.getKey())
                            .name(entry.getValue().getName())
                            .nickName(entry.getValue().getNickName())
                            .build();
                }).collect(Collectors.toList());
    }
}

