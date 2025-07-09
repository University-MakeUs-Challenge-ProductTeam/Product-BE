package umc.product.domain.noticeMember.service;

import umc.product.domain.notice.entity.Notice;

public interface AdminNoticeMemberQueryService {

    Long getReadMemberCount(Notice notice);
    Long getCheckMemberCount(Notice notice);
}
