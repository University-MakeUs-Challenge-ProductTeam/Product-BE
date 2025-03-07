package umc.product.domain.study.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "스터디 요청 사용자 정보 DTO")
@Getter
@NoArgsConstructor
public class AdminStudyMemberRequest {

    @Schema(description = "사용자 id", example = "1")
    private Long memberId;

    @Schema(description = "스터디 역할", example = "LEADER")
    private String studyRole;
}
