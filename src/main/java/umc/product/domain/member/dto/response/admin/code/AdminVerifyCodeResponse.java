package umc.product.domain.member.dto.response.admin.code;

import lombok.Builder;

@Builder
public record AdminVerifyCodeResponse(
        String university
){

}
