package umc.product.domain.notice.converter.response;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.notice.dto.response.admin.AdminNoticeResponse;
import umc.product.domain.notice.dto.response.admin.AdminNoticeDetailResponse;
import umc.product.domain.notice.entity.Notice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
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

}
