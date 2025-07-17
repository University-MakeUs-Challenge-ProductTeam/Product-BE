package umc.product.domain.noticeMember.mapper;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.noticeMember.entity.NoticeMember;

@Component
public class NoticeMemberMapper {
    
    public NoticeMember toNoticeMember(Notice notice, Member member) {
        return NoticeMember.builder()
                .notice(notice)
                .member(member)
                .isRead(false)
                .isChecked(false)
                .build();
    }
}
