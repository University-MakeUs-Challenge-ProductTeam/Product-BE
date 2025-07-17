package umc.product.domain.noticeMember.repository.querydsl;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.Notice;

import org.springframework.data.domain.Pageable;


public interface NoticeMemberDslRepository {
    Page<Member> findTargetMembersByReadStatus(Notice notice, Boolean isReadFilter, Pageable pageable);
    Page<Member> findTargetMembersByCheckStatus(Notice notice, Boolean isReadFilter, Pageable pageable);

}
