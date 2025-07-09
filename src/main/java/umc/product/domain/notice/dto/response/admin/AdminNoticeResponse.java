package umc.product.domain.notice.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "[운영진용] 공지사항 목록 DTO")
@Builder
public class AdminNoticeResponse {
    @Schema(description = "[운영진용] 공지사항 ID")
    private Long noticeId;

    @Schema(description = "[운영진용] 공지사항 제목")
    private String title;

    @Schema(description = "[운영진용] 공지사항 대상 기수")
    private List<String> targetSemester;

    @Schema(description = "[운영진용] 공지사항 대상 파트")
    private List<String> targetPart;

    @Schema(description = "[운영진용] 공지사항 조회 수")
    private Long readCount;

    @Schema(description = "[운영진용] 공지사항 이벤트 포함 여부")
    private Boolean hasEvent;

    @Schema(description = "[운영진용] 공지사항 이벤트 날짜")
    private String eventDate;

    @Schema(description = "[운영진용] 공지사항 대표 이미지 URL")
    private String imageUrl;
}
