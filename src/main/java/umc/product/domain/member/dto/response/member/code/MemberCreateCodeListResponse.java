package umc.product.domain.member.dto.response.member.code;

import lombok.Builder;
import umc.product.domain.member.dto.response.member.code.MemberCreateCodeResponse;

import java.util.List;

@Builder
public record MemberCreateCodeListResponse(
        List<MemberCreateCodeResponse> memberCodeList
) {

}
