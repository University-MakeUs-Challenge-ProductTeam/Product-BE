package umc.product.domain.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Schema(description = "프로젝트 인원 리스트 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ProjectMemberResponse {

    @Schema(description = "파트", example = "SPRING")
    private Part part;

    @Schema(description = "닉네임", example = "산호")
    private String nickname;

    @Schema(description = "이름", example = "서상효")
    private String name;
}
