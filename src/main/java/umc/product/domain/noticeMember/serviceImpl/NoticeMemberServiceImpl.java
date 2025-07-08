package umc.product.domain.noticeMember.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.noticeMember.entity.NoticeMember;
import umc.product.domain.noticeMember.mapper.NoticeMemberMapper;
import umc.product.domain.noticeMember.repository.NoticeMemberRepository;
import umc.product.domain.noticeMember.service.NoticeMemberService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeMemberServiceImpl implements NoticeMemberService {
    private final NoticeMemberRepository noticeMemberRepository;
    private final NoticeMemberMapper noticeMemberMapper;

    @Override
    @Transactional
    public NoticeMember createNoticeMember(Notice notice, Member member) {
        return noticeMemberRepository.findByNoticeAndMember(notice, member)
                .orElseGet(() -> {
                    NoticeMember noticeMember = noticeMemberMapper.toNoticeMember(notice, member);
                    return noticeMemberRepository.save(noticeMember);
                });
    }

    @Override
    public List<NoticeMember> getNoticeMembersByNotice(Notice notice) {
        return noticeMemberRepository.findByNotice(notice);
    }

    @Override
    public List<NoticeMember> getNoticeMembersByNoticeInAndMember(List<Notice> notices, Member member) {
        return noticeMemberRepository.findByNoticeInAndMember(notices, member);
    }

    @Override
    public List<NoticeMember> getNoticeMembersByNoticeInAndMemberWithFetchJoin(List<Notice> notices, Member member) {
        return noticeMemberRepository.findByNoticeInAndMemberWithFetchJoin(notices, member);
    }

    @Override
    public NoticeMember getNoticeMemberByNoticeAndMember(Notice notice, Member member) {
        return noticeMemberRepository.findByNoticeAndMember(notice, member)
                .orElse(null);
    }

    @Override
    @Transactional
    public void markAsRead(NoticeMember noticeMember) {
        noticeMember.markAsRead();
        noticeMemberRepository.save(noticeMember);
    }

    @Override
    @Transactional
    public void markAsChecked(NoticeMember noticeMember) {
        noticeMember.markAsChecked();
        noticeMemberRepository.save(noticeMember);
    }

    @Override
    public long getUnreadMemberCount(Notice notice) {
        return noticeMemberRepository.countByNoticeAndIsReadFalse(notice);
    }

    @Override
    public long getReadMemberCount(Notice notice) {
        return noticeMemberRepository.countByNoticeAndIsReadTrue(notice);
    }
}
