package umc.product.domain.study.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "스터디 멤버 정보")
@Getter
@Builder
@AllArgsConstructor
public class StudyMemberResponse {

    @Schema(description = "사용자 id", example = "1")
    private Long memberId;

    @Schema(description = "대학 이름", example = "인하대학교")
    private String university;

    @Schema(description = "닉네임", example = "델로")
    private String nickname;

    @Schema(description = "출석 상태", example = "UNSET")
    private String attendance;
}
