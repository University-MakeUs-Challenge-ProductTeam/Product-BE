package umc.product.domain.member.dto.response.admin;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.dto.response.common.MemberSearchResponse;

import java.util.List;

@Getter
@Builder
public class AdminMemberListResponse {
    private List<MemberSearchResponse> memberList;
}
