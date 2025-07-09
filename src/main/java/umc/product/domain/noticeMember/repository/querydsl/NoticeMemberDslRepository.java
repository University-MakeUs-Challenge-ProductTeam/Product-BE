package umc.product.domain.noticeMember.repository.querydsl;

import org.springframework.data.domain.Page;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.Notice;

import org.springframework.data.domain.Pageable;


public interface NoticeMemberDslRepository {
    Page<Member> findNoticeTargetMembers(Notice targetNotice, Boolean checkedFilter, Pageable pageable);
}
