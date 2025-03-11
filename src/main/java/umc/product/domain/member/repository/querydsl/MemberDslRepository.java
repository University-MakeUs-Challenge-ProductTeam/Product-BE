package umc.product.domain.member.repository.querydsl;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.semester.entity.SemesterPosition;
import umc.product.domain.university.entity.University;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MemberDslRepository {
    void updateAvatarImage(Member member, String avatarUrl);
    Optional<Member> findById(Long memberId);
    Optional<Member> findByIdForSignup(Long memberId);
    Optional<Member> findMemberByClientId(String clientId);
    List<Member> findMemberList(Pageable pageable, Member currentMember, Long semesterId, Role role, Part part);
    List<Member> findMembersBySearchString(Member member,String searchString);
    List<Member> findWaitingMemberByUniversity(University university);
    List<Member> findWaitingMember();
    Optional<Member> findByClientIdAndLoginType(String clientId, LoginType loginType);
    boolean existsMemberByClientId(String clientId);
}
