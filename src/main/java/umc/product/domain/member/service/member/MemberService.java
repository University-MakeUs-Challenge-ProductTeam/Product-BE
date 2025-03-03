package umc.product.domain.member.service.member;

import umc.product.domain.member.dto.request.admin.AdminProfileModifyRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.university.entity.University;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member findById(Long id);
    Member saveEntity(Member member);
    Member modifyMyProfileAvatar(Member member, String avatarUrl);
    List<Member> findWaitingMemberByUniversity(University university);
    List<Member> findWaitingMember();
}
