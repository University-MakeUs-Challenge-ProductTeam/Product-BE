package umc.product.domain.member.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.member.dto.response.member.MemberSearchResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;
import umc.product.domain.member.entity.enums.Role;

import java.util.List;
import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByClientIdAndLoginType(String clientId, LoginType loginType);
    Optional<Member> findByName(String name);


    @Query("select m " +
            "from Member m " +
            "where (:role is null or m.role = :role)" +
            "AND (:semester is null OR m.name = :semester)" +
            "AND (:part is null OR m.name = :part) ")
    //멤버 권한에 따라 search 범위 바뀌게
    List<Member> findMembers(Pageable pageable,
                                           @Param("semester") String semester,
                                           @Param("role") Role role,
                                           @Param("part") String part);
    //엔티티 다 만들어지면 추가
}

