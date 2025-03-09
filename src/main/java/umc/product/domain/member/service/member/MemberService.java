package umc.product.domain.member.service.member;

import umc.product.domain.member.entity.Member;
import umc.product.domain.university.entity.University;

import java.util.List;

public interface MemberService {
    Member findById(Long id);
    Member saveEntity(Member member);
    Member modifyMyProfileAvatar(Member member,
                                 String avatarUrl);
    List<Member> findWaitingMemberByUniversity(University university);
    List<Member> findWaitingMember();
}
