package umc.product.domain.member.dto.request.admin.code;

import lombok.Builder;

import java.util.List;

@Builder
public record AdminCreateCodeListResponse(
        List<AdminCreateCodeResponse> memberCodeList
) {

}
