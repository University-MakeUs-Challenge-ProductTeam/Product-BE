package umc.product.domain.notice.dto.response.admin.list;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.notice.dto.response.admin.AdminNoticeReadStatusResponse;

import java.util.List;

@Getter
@Builder
public class AdminNoticeReadStatusListResponse {
    private List<AdminNoticeReadStatusResponse> members;
    private Long readCount;
}
