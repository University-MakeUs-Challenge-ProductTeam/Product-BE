package umc.product.domain.member.converter.response;

import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.member.out.MemberOutIdResponse;
import umc.product.domain.member.entity.Member;

@Component
public class MemberOutConverter {
    public MemberOutIdResponse toMemberOutIdResponse(
            Long outId,
            Member member
    ) {
        return MemberOutIdResponse.builder()
                .memberId(member.getId())
                .outId(outId)
                .build();
    }
}
