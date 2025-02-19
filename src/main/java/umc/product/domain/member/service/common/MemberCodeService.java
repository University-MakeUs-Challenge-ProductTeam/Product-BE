package umc.product.domain.member.service.common;

import umc.product.domain.member.dto.request.common.CommonCodeRequest;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.response.common.MemberCodeResponse;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;

public interface MemberCodeService {
    public MemberCodeResponse saveAdminCode(AdminCodeRequest request, String code);
    public MemberCodeResponse saveChallengerCode(CommonCodeRequest request, String code);
    public String createAdminCode();
    public String createChallengerCode();
    public MemberRoleResponse verifyMemberCode(String code);
}
