package umc.product.domain.member.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.member.dto.request.admin.register.AdminRegisterListRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.university.entity.University;

import java.util.List;
import java.util.Optional;

public interface MemberDslRepository {
    boolean existById(Long memberId);

    List<Member> findByNameAndNickNameAndUniversity(List<AdminRegisterListRequest.AdminRegisterMemberRequest> registerMemberList);

    void updateAvatarImage(Member member, String avatarUrl);

    Optional<Member> findByIdAndFetchLoginInfo(Long memberId);
    Optional<Member> findByIdNotFetchLoginInfo(Long memberId);
    Optional<Member> findMemberByClientId(String clientId);
    Page<Member> findMemberListByFilter(Pageable pageable,
                                Member currentMember,
                                Long semesterId,
                                Role role,
                                Part part);
    Page<Member> findMembersBySearchString(Member member,
                                           Pageable pageable,
                                           String searchString);
    Optional<Member> findByClientIdAndLoginType(String clientId, LoginType loginType);
    boolean existsMemberByClientId(String clientId);
    long countMemberByFilter(Member member, Long semesterId, Role role, Part part);
}
