package umc.product.domain.notice.converter.response;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.notice.dto.response.admin.AdminNoticeCheckStatusResponse;
import umc.product.domain.notice.dto.response.admin.AdminNoticeReadStatusResponse;
import umc.product.domain.notice.dto.response.admin.AdminNoticeResponse;
import umc.product.domain.notice.dto.response.admin.AdminNoticeDetailResponse;
import umc.product.domain.notice.dto.response.admin.list.AdminNoticeCheckStatusListResponse;
import umc.product.domain.notice.dto.response.admin.list.AdminNoticeListResponse;
import umc.product.domain.notice.dto.response.admin.list.AdminNoticeReadStatusListResponse;
import umc.product.domain.notice.entity.Notice;
import umc.product.domain.noticeMember.entity.NoticeMember;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminNoticeConverter {

    // [운영진용] 공지 목록 응답 변환
    public AdminNoticeResponse toAdminNoticeResponse(Notice notice, Long readCount) {
        return AdminNoticeResponse.builder()
                .noticeId(notice.getId())
                .writerId(notice.getWriter().getId())
                .title(notice.getTitle())
                .targetSemester(
                        notice.getNoticeSemesters().stream()
                                .map(ns -> ns.getSemester().getName())
                                .collect(Collectors.toList())
                )
                .targetPart(
                        notice.getNoticeParts().stream()
                                .map(np -> np.getPart().getName())
                                .collect(Collectors.toList())
                )
                .readCount(readCount)
                .hasEvent(notice.getEvent() != null)
                .eventDate(notice.getEvent() != null ? notice.getEvent().getEventStartDate().toString() : null)
                .imageUrl(notice.getImages())
                .build();
    }

    // [운영진용] 공지 목록 응답 변환 (페이징)
    public AdminNoticeListResponse toAdminNoticeListResponse(Page<Notice> notices) {
        return AdminNoticeListResponse.builder()
                .adminNoticeResponse(
                        notices.getContent().stream()
                                .map(notice -> {
                                    Long readCount = notice.getNoticeMembers().stream()
                                            .filter(NoticeMember::getIsRead)
                                            .count();
                                    return toAdminNoticeResponse(notice, readCount);
                                })
                                .collect(Collectors.toList()))
                .totalPages(notices.getTotalPages())
                .totalElements(notices.getTotalElements())
                .page(notices.getNumber() + 1) // 페이지는 0부터 시작하므로 +1
                .size( notices.getSize()) // 페이지당 공지사항 수
                .build();
    }

    // [운영진용] 공지 상세 응답 변환
    public AdminNoticeDetailResponse toAdminNoticeDetailResponse(Notice notice, Long readCount, Long checkCount) {
        return new AdminNoticeDetailResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                splitByComma(notice.getImages()),
                notice.getNoticeDate(),
                notice.getCheckDeadline(),
                splitByComma(notice.getHashtags()),
                readCount,
                checkCount,
                notice.getEvent() != null ? notice.getEvent().getTitle() : null,
                notice.getEvent() != null ? notice.getEvent().getId() : null
        );
    }

    private List<String> splitByComma(String raw) {
        if (raw == null || raw.isBlank()) return List.of();
        return Arrays.stream(raw.split(","))
                .map(String::trim)
                .toList();
    }

    // [운영진용] 공지 체크 여부 응답 변환
    public AdminNoticeCheckStatusListResponse toAdminNoticeCheckStatusResponsePage(Page<Member> targetMembers, Notice notice, Long totalCheckedCount) {
        List<NoticeMember> noticeMembers = notice.getNoticeMembers();

        // 멤버 각각에 대해 응답 DTO로 변환
        List<AdminNoticeCheckStatusResponse> memberResponses = targetMembers.stream().map(member -> {
            NoticeMember nm = noticeMembers.stream()
                    .filter(n -> n.getMember().getId().equals(member.getId()))
                    .findFirst()
                    .orElse(null);

            boolean isChecked = nm != null && Boolean.TRUE.equals(nm.getIsChecked());

            return AdminNoticeCheckStatusResponse.builder()
                    .memberId(member.getId())
                    .profileUrl(member.getAvatarUrl())
                    .name(member.getName())
                    .nickName(member.getNickName())
                    .universityName(member.getUniversity().getName())
                    .isChecked(isChecked)
                    .build();
        }).toList();

        // AdminNoticeCheckStatusListResponse 생성
        return AdminNoticeCheckStatusListResponse.builder()
                .members(memberResponses)
                .checkedCount(totalCheckedCount)
                .page(targetMembers.getNumber() + 1) // 0부터 시작하므로 +1
                .size(targetMembers.getSize())
                .totalElements(targetMembers.getTotalElements())
                .totalPages(targetMembers.getTotalPages())
                .build();
    }

    // [운영진용] 공지 열람 여부 응답 변환
    public AdminNoticeReadStatusListResponse toAdminNoticeReadStatusResponsePage(
            Page<Member> targetMembers,
            Notice notice,
            Long totalReadCount) {

        List<NoticeMember> noticeMembers = notice.getNoticeMembers();

        // 멤버 목록을 열람 여부 응답 DTO로 변환
        List<AdminNoticeReadStatusResponse> memberResponses = targetMembers.stream().map(member -> {
            NoticeMember nm = noticeMembers.stream()
                    .filter(n -> n.getMember().getId().equals(member.getId()))
                    .findFirst()
                    .orElse(null);

            boolean isRead = nm != null && Boolean.TRUE.equals(nm.getIsRead());

            return AdminNoticeReadStatusResponse.builder()
                    .memberId(member.getId())
                    .profileUrl(member.getAvatarUrl())
                    .name(member.getName())
                    .nickName(member.getNickName())
                    .universityName(member.getUniversity().getName())
                    .isRead(isRead)
                    .build();
        }).toList();

        // 전체 리스트를 단일 DTO로 래핑
        return AdminNoticeReadStatusListResponse.builder()
                .members(memberResponses)
                .readCount(totalReadCount)
                .page(targetMembers.getNumber() + 1) // 0-based → 1-based
                .size(targetMembers.getSize())
                .totalElements(targetMembers.getTotalElements())
                .totalPages(targetMembers.getTotalPages())
                .build();
    }




}
