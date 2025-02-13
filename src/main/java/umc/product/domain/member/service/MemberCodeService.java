package umc.product.domain.member.service;

import umc.product.domain.member.dto.request.MemberCodeRequest;
import umc.product.domain.member.dto.response.MemberCodeResponse;
import umc.product.domain.member.entity.RefreshToken;

public interface MemberCodeService {
    public MemberCodeResponse saveCode(MemberCodeRequest request, String code);
}
