package umc.product.domain.member.dto.response.member.code;

import lombok.Builder;
import umc.product.domain.member.entity.enums.Part;

import java.util.List;

@Builder
public record MemberCodeVerifyResponse (
        Long memberId,
        String name,
        String nickName,
        Part part,
        List<String> positionList
){

}
