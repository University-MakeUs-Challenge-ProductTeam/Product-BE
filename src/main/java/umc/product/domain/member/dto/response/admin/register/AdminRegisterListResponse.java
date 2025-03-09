package umc.product.domain.member.dto.response.admin.register;

import lombok.Builder;

import java.util.List;

@Builder
public record AdminRegisterListResponse(
        List<AdminRegisterMemberResponse> memberCodeList
) {
    @Builder
    public record AdminRegisterMemberResponse(
            Long memberId,
            String code,
            String name,
            String nickName
    ){

    }
}
