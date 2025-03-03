package umc.product.domain.member.service.admin;

import umc.product.domain.member.dto.request.admin.AdminOutRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.OutReason;

public interface AdminOutService {

    void postMemberOut(Member member, OutReason outReason);
    void modifyMemberOut(Long outId, OutReason outReason);
    void deleteMemberOut(Member member,Long outId);
}
