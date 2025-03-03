package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.admin.AdminCodeResponse;
import umc.product.domain.member.dto.response.admin.AdminCodeVerifyResponse;
import umc.product.domain.member.dto.response.admin.AdminOutIdResponse;
import umc.product.domain.member.dto.response.member.MemberCodeInfoResponse;
import umc.product.domain.member.dto.response.member.MemberCodeResponse;
import umc.product.domain.member.dto.response.member.MemberCodeVerifyResponse;
import umc.product.domain.member.entity.Member;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MemberCodeConverter {

    public AdminCodeResponse toAdminCodeResponse(String code){
        return AdminCodeResponse.builder()
                .code(code)
                .build();
    }

    public MemberCodeResponse toMemberCodeResponse(Map<String, Member> codeMap){
        List<MemberCodeInfoResponse> memberCodeInfoList = toMemberCodeInfoResponse(codeMap);
        return MemberCodeResponse.builder()
                .memberCodeList(memberCodeInfoList)
                .build();
    }

    public AdminCodeVerifyResponse toAdminCodeVerifyResponse(String universityName) {
        return AdminCodeVerifyResponse.builder()
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

    private List<MemberCodeInfoResponse> toMemberCodeInfoResponse(Map<String, Member> codeMap) {
        return codeMap.entrySet().stream()
                .map(entry -> {
                    return MemberCodeInfoResponse.builder()
                            .memberId(entry.getValue().getId())
                            .code(entry.getKey())
                            .name(entry.getValue().getName())
                            .nickName(entry.getValue().getNickName())
                            .build();
                }).collect(Collectors.toList());
    }
}

