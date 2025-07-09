package umc.product.domain.notice.dto.response.member.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.notice.dto.response.member.NoticeResponse;

import java.util.List;

@Schema(description = "공지사항 목록 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class NoticeListResponse {

    @Schema(description = "공지사항 목록 리스트")
    private final List<NoticeResponse> noticeResponseList;
} 