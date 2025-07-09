package umc.product.domain.notice.dto.response.admin.list;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.notice.dto.response.admin.AdminNoticeCheckStatusResponse;

import java.util.List;

@Getter
@Builder
public class AdminNoticeCheckStatusListResponse {
    private List<AdminNoticeCheckStatusResponse> members;
    private Long checkedCount;
}
