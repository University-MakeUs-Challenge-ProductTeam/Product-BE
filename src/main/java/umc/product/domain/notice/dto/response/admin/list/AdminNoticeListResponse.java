package umc.product.domain.notice.dto.response.admin.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.notice.dto.response.admin.AdminNoticeResponse;

import java.util.List;

@Schema(description = "[운영진용] 공지사항 목록 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class AdminNoticeListResponse {
    @Schema(description = "[운영진용] 공지사항 목록 리스트")
    private final List<AdminNoticeResponse> adminNoticeResponse;

    @Schema(description = "[운영진용] 현재 페이지 번호")
    private int page;

    @Schema(description = "[운영진용] 페이지당 공지사항 수")
    private int size;

    @Schema(description = "[운영진용] 전체 공지사항 수")
    private Long totalElements;

    @Schema(description = "[운영진용] 전체 페이지 수")
    private int totalPages;
}
