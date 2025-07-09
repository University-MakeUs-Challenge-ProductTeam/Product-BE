package umc.product.domain.noticeMember.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.noticeMember.entity.NoticeMember;

import java.util.List;
import java.util.Optional;

public interface NoticeMemberRepository extends JpaRepository<NoticeMember, Long> {
    List<NoticeMember> findByNotice(Notice notice);
    List<NoticeMember> findByMember(Member member);
    List<NoticeMember> findByNoticeInAndMember(List<Notice> notices, Member member);
    
    @Query("SELECT nm FROM NoticeMember nm JOIN FETCH nm.notice WHERE nm.notice IN :notices AND nm.member = :member")
    List<NoticeMember> findByNoticeInAndMemberWithFetchJoin(@Param("notices") List<Notice> notices, @Param("member") Member member);
    
    Optional<NoticeMember> findByNoticeAndMember(Notice notice, Member member);
    long countByNoticeAndIsReadFalse(Notice notice);
    long countByNoticeAndIsReadTrue(Notice notice);
    long countByNoticeAndIsCheckedTrue(Notice notice);

    Optional<NoticeMember> findByMemberAndNotice(Member member, Notice notice);
    List<NoticeMember> findAllByNoticeAndIsCheckedTrue(Notice notice);
}
