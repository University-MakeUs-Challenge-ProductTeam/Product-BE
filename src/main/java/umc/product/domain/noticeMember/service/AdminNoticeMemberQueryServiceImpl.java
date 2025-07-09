package umc.product.domain.noticeMember.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.noticeMember.repository.NoticeMemberRepository;

@Service
@AllArgsConstructor
public class AdminNoticeMemberQueryServiceImpl implements AdminNoticeMemberQueryService{

    private final NoticeMemberRepository noticeMemberRepository;

    @Override
    public Long getReadMemberCount(Notice notice) {
        return noticeMemberRepository.countByNoticeAndIsReadTrue(notice);
    }

    @Override
    public Long getCheckMemberCount(Notice notice) {
        return noticeMemberRepository.countByNoticeAndIsCheckedTrue(notice);
    }
}
