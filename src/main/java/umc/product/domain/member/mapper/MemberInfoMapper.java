package umc.product.domain.member.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberLoginInfo;

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

