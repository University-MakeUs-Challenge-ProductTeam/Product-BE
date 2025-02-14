package umc.product.domain.member.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberCodeResponse;
import umc.product.domain.member.dto.response.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberCode;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.config.security.jwt.TokenInfo;

@Component
public class MemberCodeMapper {
    public MemberCodeResponse toMemberCode(String code){
        return MemberCodeResponse.builder()
                .code(code)
                .build();
    }
}

