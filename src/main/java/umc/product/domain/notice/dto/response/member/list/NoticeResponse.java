package umc.product.domain.notice.dto.response.member.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import umc.product.domain.notice.entity.enums.NoticeTarget;

@Schema(description = "공지사항 응답 DTO")
@Builder
public class NoticeResponse {

    @Schema(description = "공지사항 id", example = "1")
    private Long noticeId;

    @Schema(description = "공지사항 대상", example = "ALL")
    private NoticeTarget target;

    @Schema(description = "공지사항 제목", example = "중요 공지사항")
    private String title;

    @Schema(description = "열람 여부", example = "true")
    private Boolean isRead;
} 