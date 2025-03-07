package umc.product.domain.member.dto.request.admin.register;

import lombok.Builder;

import java.util.List;

@Builder
public record AdminRegisterResponse(
        List<AdminRegisterMemberResponse> memberCodeList
) {

}
