package umc.product.domain.member.dto.response.admin.search;

import lombok.Builder;
import umc.product.domain.member.dto.response.member.search.MemberSearchResponse;

import java.util.List;

@Builder
public record AdminMemberSearchListResponse(
        List<MemberSearchResponse> memberList
){

}
