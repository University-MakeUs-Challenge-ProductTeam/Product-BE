package umc.product.domain.member.service;

import umc.product.domain.member.dto.request.code.MemberChallengerCodeRequest;
import umc.product.domain.member.dto.request.code.MemberAdminCodeRequest;
import umc.product.domain.member.dto.response.code.MemberCodeResponse;
import umc.product.domain.member.dto.response.code.MemberCodeRoleResponse;

public interface MemberCodeService {
    public MemberCodeResponse saveAdminCode(MemberAdminCodeRequest request, String code);
    public MemberCodeResponse saveChallengerCode(MemberChallengerCodeRequest request, String code);
    public String createAdminCode();
    public String createChallengerCode();
    public MemberCodeRoleResponse verifyMemberCode(String code);
}
