package umc.product.domain.member.service.member;

import umc.product.domain.member.entity.Member;
import umc.product.domain.university.entity.University;

import java.util.List;

public interface MemberService {
    void existById(Long memberId);
    Member findById(Long memberId);
    Member findByIdNotFetchLoginInfo(Long memberId);
    void modifyMyProfileAvatar(Member member,
                                 String avatarUrl);
}
