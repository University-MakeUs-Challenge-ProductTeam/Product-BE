package umc.product.domain.notice.dto.response.admin.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.notice.dto.response.admin.AdminNoticeResponse;

@Schema(description = "[운영진용] 공지사항 목록 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class AdminNoticeListResponse {
    @Schema(description = "[운영진용] 공지사항 목록 리스트")
    private final AdminNoticeResponse adminNoticeResponse;
}
