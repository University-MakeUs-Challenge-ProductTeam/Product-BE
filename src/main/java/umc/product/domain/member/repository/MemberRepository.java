package umc.product.domain.member.repository;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.university.entity.University;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    public List<Member> findMembers(Pageable pageable, Member currentMember, Long semesterId, Role role, Part part);
    public List<Member> findMembersBySearchString(Member member,String searchString);
    public void saveRegisterMembers(List<Member> memberList, List<SemesterPosition> semesterPositionList);
    public List<Member> findWaitingMemberByUniversity(University university);
    public List<Member> findWaitingMember();
    Optional<Member> findByClientIdAndLoginType(String clientId, LoginType loginType);
    boolean existsMemberByClientId(String clientId);
}
