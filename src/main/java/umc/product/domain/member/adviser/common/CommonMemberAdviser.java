package umc.product.domain.member.adviser.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;
import umc.product.domain.member.service.common.MemberCodeService;

@Component
@RequiredArgsConstructor
public class CommonMemberAdviser {
    private final MemberCodeService memberCodeService;

    public MemberRoleResponse verifyMemberCode(String code) {
        return memberCodeService.verifyMemberCode(code);
    }


}
