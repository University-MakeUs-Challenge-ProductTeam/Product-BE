package umc.product.domain.member.adviser.challenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;
import umc.product.domain.member.service.MemberCodeService;

@Component
@RequiredArgsConstructor
public class ChallengerMemberAdviser {
    private final MemberCodeService memberCodeService;

    public MemberRoleResponse verifyMemberCode(String code) {
        return memberCodeService.verifyMemberCode(code);
    }


}
