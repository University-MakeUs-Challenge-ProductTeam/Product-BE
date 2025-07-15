package umc.product.domain.notice.service;

import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.dto.request.admin.AdminNoticeRequest;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.status.NoticeErrorStatus;
import umc.product.domain.semester.entity.Semester;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

public interface AdminNoticeCommandService {
    Notice createNotice(AdminNoticeRequest request, Member writer, List<Semester> semesters);
    Notice updateNotice(Long noticeId, AdminNoticeRequest request, Member writer, List<Semester> semesters);
    void deleteNotice(Long noticeId);

}
