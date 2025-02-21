package umc.product.domain.member.service.admin;

import umc.product.domain.member.dto.request.common.CommonCodeRequest;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.dto.response.common.MemberCodeResponse;
import umc.product.domain.member.dto.response.common.MemberRoleResponse;

public interface AdminCodeService {
    public void saveAdminCode(AdminCodeRequest request, String code);
    public void saveChallengerCode(CommonCodeRequest request, String code);
    public String createAdminCode();
    public String createChallengerCode();
}
