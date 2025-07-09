package umc.product.domain.notice.converter.response;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.notice.dto.response.admin.AdminNoticeResponse;
import umc.product.domain.notice.entity.Notice;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdminNoticeConverter {

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
}
