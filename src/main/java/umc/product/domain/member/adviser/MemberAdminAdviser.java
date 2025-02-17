package umc.product.domain.member.adviser;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.dto.request.MemberAdminSignUpRequest;
import umc.product.domain.member.dto.request.code.MemberChallengerCodeRequest;
import umc.product.domain.member.dto.request.code.MemberAdminCodeRequest;
import umc.product.domain.member.dto.response.common.MemberIdResponse;
import umc.product.domain.member.dto.response.code.MemberCodeResponse;
import umc.product.domain.member.dto.response.member.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.MemberAdminService;
import umc.product.domain.member.service.MemberCodeService;
import umc.product.domain.member.service.MemberService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MemberAdminAdviser {
    private final MemberCodeService memberCodeService;
    private final MemberAdminService memberAdminService;
    private final MemberService memberService;

    public MemberIdResponse signUp(MemberAdminSignUpRequest request){
        return memberAdminService.signUp(request);
    }

    public MemberCodeResponse createAdminCode(MemberAdminCodeRequest request) {
        String code = memberCodeService.createAdminCode();
        return memberCodeService.saveAdminCode(request, code);
    }

    public MemberCodeResponse createChallengerCode(MemberChallengerCodeRequest request) {
        String code = memberCodeService.createChallengerCode();
        return memberCodeService.saveChallengerCode(request, code);
    }

    public List<MemberSearchResponse> searchMembers(Member member, Pageable pageable, String semester, Role role, String part) {
        return memberService.findMembers(member, pageable, semester, role, part);
    }
}
