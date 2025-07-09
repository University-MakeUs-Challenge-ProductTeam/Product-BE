package umc.product.domain.notice.service;

import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.dto.request.admin.AdminNoticeRequest;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.status.NoticeErrorStatus;
import umc.product.global.common.exception.RestApiException;

public interface AdminNoticeCommandService {
    Notice createNotice(AdminNoticeRequest request, Member writer);
    Notice updateNotice(Long noticeId, AdminNoticeRequest request, Member writer);
    void deleteNotice(Long noticeId);

}
