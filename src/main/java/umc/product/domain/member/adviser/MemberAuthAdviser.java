package umc.product.domain.member.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;
import umc.product.domain.member.service.MemberAuthService;
import umc.product.domain.member.service.MemberCodeService;

@Component
@RequiredArgsConstructor
public class MemberAuthAdviser {
    private final MemberAuthService memberAuthService;
    private final MemberCodeService memberCodeService;

    public MemberRoleResponse verifyMemberCode(String code) {
        return memberCodeService.verifyMemberCode(code);
    }


}
