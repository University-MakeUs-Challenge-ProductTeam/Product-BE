package umc.product.domain.notice.adviser;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.converter.response.AdminNoticeConverter;
import umc.product.domain.notice.dto.request.admin.AdminNoticeListRequest;
import umc.product.domain.notice.dto.request.admin.AdminNoticeRequest;
import umc.product.domain.notice.dto.response.admin.AdminNoticeDetailResponse;
import umc.product.domain.notice.dto.response.admin.AdminNoticeIdResponse;
import umc.product.domain.notice.dto.response.admin.AdminNoticeResponse;
import umc.product.domain.notice.dto.response.admin.list.AdminNoticeCheckStatusListResponse;
import umc.product.domain.notice.dto.response.admin.list.AdminNoticeListResponse;
import umc.product.domain.notice.dto.response.admin.list.AdminNoticeReadStatusListResponse;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.notice.entity.NoticeSemester;
import umc.product.domain.notice.service.AdminNoticeCommandService;
import umc.product.domain.notice.service.AdminNoticeQueryService;
import umc.product.domain.noticeMember.service.AdminNoticeMemberQueryService;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.service.SemesterService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminNoticeAdviser {

    private final AdminNoticeQueryService adminNoticeQueryService;
    private final AdminNoticeCommandService adminNoticeCommandService;
    private final AdminNoticeMemberQueryService adminNoticeMemberQueryService;
    private final SemesterService semesterService;


    private final AdminNoticeConverter converter;

    // [운영진용] 공지 목록 조회
    public AdminNoticeListResponse getAdminNoticeList(AdminNoticeListRequest request, Pageable pageable) {
        // 공지 목록 조회
        Page<Notice> noticePage = adminNoticeQueryService.getAdminNoticeList(request, pageable);

        // 공지 목록을 AdminNoticeResponse로 변환
        return converter.toAdminNoticeListResponse(noticePage);
    }

    // [운영진용] 공지 상세 조회
    public AdminNoticeDetailResponse getAdminNoticeDetail(Long noticeId) {
        Notice notice = adminNoticeQueryService.getNoticeById(noticeId);

        Long readCount = adminNoticeMemberQueryService.getReadMemberCount(notice);
        Long checkCount = adminNoticeMemberQueryService.getCheckMemberCount(notice); // 열람 체크 수

        return converter.toAdminNoticeDetailResponse(notice, readCount, checkCount);
    }

    // [운영진용] 공지 체크 상태 조회 todo true, false 반대로 되는 에러 수정 필요
    public AdminNoticeCheckStatusListResponse getNoticeCheckMemberList(Long noticeId, Boolean isChecked, Pageable pageable) {
        // 공지 조회
        Notice notice = adminNoticeQueryService.getNoticeById(noticeId);

        // 대상 멤버 조회 (열람 체크 여부 필터링, true=체크함, false=체크하지 않음, null=모두 조회)
        Page<Member> targetMembers = adminNoticeMemberQueryService.getNoticeTargetMembersByCheckStatus(notice, isChecked, pageable);

        Long checkCount = adminNoticeMemberQueryService.getCheckMemberCount(notice); // 열람 체크 수

        return converter.toAdminNoticeCheckStatusResponsePage(targetMembers, notice, checkCount);
    }

    // [운영진용] 공지 열람 상태 조회 todo true, false 반대로 되는 에러 수정 필요
    public AdminNoticeReadStatusListResponse getNoticeReadMemberList(Long noticeId, Boolean isChecked, Pageable pageable) {
        // 공지 조회
        Notice notice = adminNoticeQueryService.getNoticeById(noticeId);

        // 대상 멤버 조회 (열람 여부 필터링, true=읽음, false=읽지 않음, null=모두 조회)
        Page<Member> targetMembers = adminNoticeMemberQueryService.getNoticeTargetMembersByReadStatus(notice, isChecked, pageable);

        Long readCount = adminNoticeMemberQueryService.getReadMemberCount(notice); // 읽은 멤버 수

        return converter.toAdminNoticeReadStatusResponsePage(targetMembers, notice, readCount);
    }

    // [운영진용] 공지 생성
    public AdminNoticeIdResponse createNotice(AdminNoticeRequest request, Member writer) {
        List<Semester> semesters = semesterService.getSemestersByIdsOrEmpty(request.semesterIds());

        Notice notice = adminNoticeCommandService.createNotice(request, writer, semesters);
        return new AdminNoticeIdResponse(notice.getId());
    }

    // [운영진용] 공지 수정
    public AdminNoticeIdResponse updateNotice(Long noticeId, AdminNoticeRequest request, Member writer) {
        List<Semester> semesters = semesterService.getSemestersByIdsOrEmpty(request.semesterIds());

        Notice notice = adminNoticeCommandService.updateNotice(noticeId, request, writer, semesters);
        return new AdminNoticeIdResponse(notice.getId());
    }

    // [운영진용] 공지 삭제
    public AdminNoticeIdResponse deleteNotice(Long noticeId) {
        adminNoticeCommandService.deleteNotice(noticeId);
        return new AdminNoticeIdResponse(noticeId);
    }
}
