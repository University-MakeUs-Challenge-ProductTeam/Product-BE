package umc.product.domain.noticeMember.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.noticeMember.entity.NoticeMember;

import java.util.List;

public interface AdminNoticeMemberQueryService {

    Long getReadMemberCount(Notice notice);
    Long getCheckMemberCount(Notice notice);
    Page<Member> getNoticeTargetMembersByReadStatus(Notice notice, Boolean isReadFilter, Pageable pageable);
    Page<Member> getNoticeTargetMembersByCheckStatus(Notice notice, Boolean isReadFilter, Pageable pageable);
    NoticeMember getNullableNoticeMemberByMemberAndNotice(Member member, Notice notice);
    List<Long> getCheckedMemberIds(Notice notice);
    List<Long> getReadMemberIds(Notice notice);

}
