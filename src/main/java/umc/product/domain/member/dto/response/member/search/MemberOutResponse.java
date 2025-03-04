package umc.product.domain.member.dto.response.member.search;

import lombok.Builder;

@Builder
public record MemberOutResponse (
        Long outId,
        String outReason

){
}
