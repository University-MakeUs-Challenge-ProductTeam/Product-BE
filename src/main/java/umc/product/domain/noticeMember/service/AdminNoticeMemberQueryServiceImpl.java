package umc.product.domain.noticeMember.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.noticeMember.entity.NoticeMember;
import umc.product.domain.noticeMember.repository.NoticeMemberRepository;
import umc.product.domain.noticeMember.repository.querydsl.NoticeMemberDslRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AdminNoticeMemberQueryServiceImpl implements AdminNoticeMemberQueryService{

    private final NoticeMemberRepository noticeMemberRepository;
    private final NoticeMemberDslRepository noticeMemberDslRepository;

    // 공지 열람 멤버 수 조회
    @Override
    public Long getReadMemberCount(Notice notice) {
        return noticeMemberRepository.countByNoticeAndIsReadTrue(notice);
    }

    // 공지 열람 체크 멤버 수 조회
    @Override
    public Long getCheckMemberCount(Notice notice) {
        return noticeMemberRepository.countByNoticeAndIsCheckedTrue(notice);
    }

    // 공지 대상 멤버 목록 조회
    @Override
    public Page<Member> getNoticeTargetMembers(Notice notice, Boolean checked, Pageable pageable) {
        return noticeMemberDslRepository.findNoticeTargetMembers(notice, checked, pageable);
    }

    // 멤버, 공지에 맞는 NoticeMember 조회 (Nullable)
    @Override
    public NoticeMember getNullableNoticeMemberByMemberAndNotice(Member member, Notice notice) {
        return noticeMemberRepository.findByMemberAndNotice(member, notice)
                .orElse(null);
    }

    // 공지 체크한 멤버 ID 목록 조회
    @Override
    public List<Long> getCheckedMemberIds(Notice notice) {
        return noticeMemberRepository.findAllByNoticeAndIsCheckedTrue(notice).stream()
                .map(nm -> nm.getMember().getId())
                .toList();
    }


}
