package umc.product.domain.notice.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
@Schema(description = "[운영진용] 공지 대상 멤버 체크 응답")
public class AdminNoticeCheckStatusResponse {
    @Schema(description = "멤버 ID")
    private Long memberId;

    @Schema(description = "프로필 이미지 URL")
    private String profileUrl;

    @Schema(description = "멤버 이름")
    private String name;

    @Schema(description = "멤버 닉네임")
    private String nickName;

    @Schema(description = "대학교 이름")
    private String universityName;

    @Schema(description = "공지사항 열람 여부", example = "true")
    private Boolean isChecked;
}
