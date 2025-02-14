package umc.product.domain.member.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.MemberSignUpRequest;
import umc.product.domain.member.dto.response.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.config.security.jwt.TokenInfo;

@Component
public class MemberInfoMapper {
    public MemberLoginInfo toMemberInfo(String id, String password, Member member){
        return MemberLoginInfo.builder()
                .memberLoginId(id)
                .password(password)
                .member(member)
                .build();
    }
}

