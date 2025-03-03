package umc.product.domain.member.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberCodeResponse {
    private List<MemberCodeInfoResponse> memberCodeList;
}
