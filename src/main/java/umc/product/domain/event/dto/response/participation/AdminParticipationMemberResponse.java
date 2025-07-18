package umc.product.domain.event.dto.response.participation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.event.entity.participation.ParticipationStatus;

@Getter
@Builder
public class AdminParticipationMemberResponse {

    @Schema(description = "행사 참여 ID", example = "1")
    private Long participationEventId;

    @Schema(description = "닉네임", example = "벡스")
    private String nickName;

    @Schema(description = "이름", example = "김준석")
    private String name;

    @Schema(description = "학교", example = "인하대학교")
    private String university;

    @Schema(description = "프로필 이미지 url", example = "profile-image-url")
    private String profileImage;

    @Schema(description = "행사 참석 상태(참석,불참석)", example = "참석")
    private ParticipationStatus participationStatus;
}
