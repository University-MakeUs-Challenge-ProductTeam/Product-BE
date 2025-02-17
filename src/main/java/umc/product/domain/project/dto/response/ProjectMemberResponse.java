package umc.product.domain.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

import java.util.List;

@Schema(description = "프로젝트 인원 리스트 조회 응답 DTO")
@Getter
@Builder
public class ProjectMemberResponse {

    @Schema(description = "멤버 id", example = "1")
    private Long memberId;

    @Schema(description = "닉네임", example = "산호")
    private String nickname;

    @Schema(description = "이름", example = "서상효")
    private String name;

    @Schema(description = "담당 파트", example = "[\"PLAN\", \"DESIGN\"]")
    private List<Part> parts;  // 한 프로젝트에서 여러 개의 파트를 담당할 수도 있기 때문에 List로 관리

    @QueryProjection
    public ProjectMemberResponse(Long memberId, String nickname, String name, List<Part> parts) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.name = name;
        this.parts = parts;
    }
}