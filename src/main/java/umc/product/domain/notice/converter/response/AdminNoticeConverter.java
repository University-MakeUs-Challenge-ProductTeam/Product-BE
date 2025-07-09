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
    public Page<AdminNoticeCheckStatusListResponse> toAdminNoticeCheckStatusResponsePage(Page<Member> targetMembers, Notice notice, Long totalCheckedCount) {
        List<NoticeMember> noticeMembers = notice.getNoticeMembers();

        return targetMembers.map(member -> {
            // 멤버에 해당하는 NoticeMember 찾기
            NoticeMember nm = noticeMembers.stream()
                    .filter(n -> n.getMember().getId().equals(member.getId()))
                    .findFirst()
                    .orElse(null);

            // 체크 표시 여부 확인 ( nm이 null일 경우 false로 처리)
            boolean isChecked = nm != null && Boolean.TRUE.equals(nm.getIsChecked());

            AdminNoticeCheckStatusResponse response = AdminNoticeCheckStatusResponse.builder()
                    .memberId(member.getId())
                    .profileUrl(member.getAvatarUrl())
                    .name(member.getName())
                    .nickName(member.getNickName())
                    .universityName(member.getUniversity().getName())
                    .isChecked(isChecked)
                    .build();

            return AdminNoticeCheckStatusListResponse.builder()
                    .members(List.of(response)) // 단일 member 담기
                    .checkedCount(totalCheckedCount)
                    .build();
        });
    }

    // [운영진용] 공지 열람 여부 응답 변환
    public Page<AdminNoticeReadStatusListResponse> toAdminNoticeReadStatusResponsePage(Page<Member> targetMembers, Notice notice, Long totalReadCount) {
        List<NoticeMember> noticeMembers = notice.getNoticeMembers();

        return targetMembers.map(member -> {
            // 멤버에 해당하는 NoticeMember 찾기
            NoticeMember nm = noticeMembers.stream()
                    .filter(n -> n.getMember().getId().equals(member.getId()))
                    .findFirst()
                    .orElse(null);

            // 열람 여부 확인 ( nm이 null일 경우 false로 처리)
            boolean isRead = nm != null && Boolean.TRUE.equals(nm.getIsRead());

            // AdminNoticeReadStatusResponse 생성
            AdminNoticeReadStatusResponse response = AdminNoticeReadStatusResponse.builder()
                    .memberId(member.getId())
                    .profileUrl(member.getAvatarUrl())
                    .name(member.getName())
                    .nickName(member.getNickName())
                    .universityName(member.getUniversity().getName())
                    .isRead(isRead)
                    .build();

            return AdminNoticeReadStatusListResponse.builder()
                    .members(List.of(response)) // 단일 member 담기
                    .readCount(totalReadCount)
                    .build();
        });
    }



}
