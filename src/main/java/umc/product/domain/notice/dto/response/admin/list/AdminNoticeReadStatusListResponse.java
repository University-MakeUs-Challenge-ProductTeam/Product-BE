package umc.product.domain.notice.dto.response.admin.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.notice.dto.response.admin.AdminNoticeReadStatusResponse;

import java.util.List;

@Getter
@Builder
public class AdminNoticeReadStatusListResponse {

    @Schema(description = "[운영진용] 공지사항 확인 상태 멤버 목록")
    private List<AdminNoticeReadStatusResponse> members;

    @Schema(description = "[운영진용] 공지사항 확인 상태 총 개수")
    private Long readCount;

    @Schema(description = "[운영진용] 현재 페이지 번호")
    private int page;

    @Schema(description = "[운영진용] 페이지당 공지사항 수")
    private int size;

    @Schema(description = "[운영진용] 전체 공지사항 수")
    private Long totalElements;

    @Schema(description = "[운영진용] 전체 페이지 수")
    private int totalPages;
}
