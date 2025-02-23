package umc.product.domain.member.service.admin;

import umc.product.domain.member.dto.request.admin.AdminCodeRequest;

public interface AdminCodeService {
    public void saveAdminCode(AdminCodeRequest request, String code);
    public String createChallengerCode();
    public String createAdminCode();
}
