package umc.product.domain.member.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Role;
import umc.product.global.common.enums.Status;

@Getter
@Builder
@AllArgsConstructor
public class MemberSearchResponse {
    private Long memberId;
    private String avatarUrl;
    private String name;
    private String nickName;
    private String university;
    private String role;
    private Status status;
}
