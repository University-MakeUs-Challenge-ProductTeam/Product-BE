package umc.product.domain.member.repository;

import org.springframework.data.domain.Pageable;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;

public interface MemberCustomRepository {
    public List<Member> findMembers(Pageable pageable, Member currentMember, String semester, Role role, String part);
    public List<Member> findMembersBySearchString(String searchString);
}
