package umc.product.domain.noticeMember.service;

import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.noticeMember.entity.NoticeMember;

import java.util.List;

public interface NoticeMemberService {
    NoticeMember createNoticeMember(Notice notice, Member member);
    List<NoticeMember> getNoticeMembersByNotice(Notice notice);
    List<NoticeMember> getNoticeMembersByNoticeInAndMember(List<Notice> notices, Member member);
    List<NoticeMember> getNoticeMembersByNoticeInAndMemberWithFetchJoin(List<Notice> notices, Member member);
    NoticeMember getNoticeMemberByNoticeAndMember(Notice notice, Member member);
    void markAsRead(NoticeMember noticeMember);
    void markAsChecked(NoticeMember noticeMember);
    long getUnreadMemberCount(Notice notice);
    long getReadMemberCount(Notice notice);
}
