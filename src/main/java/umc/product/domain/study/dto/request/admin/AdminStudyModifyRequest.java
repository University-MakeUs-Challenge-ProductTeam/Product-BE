package umc.product.domain.study.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "스터디 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class AdminStudyModifyRequest {

    @Schema(description = "스터디 타입", example = "BRANCH")
    private String studyType;

    @Schema(
            description = "스터디 참여 사용자 목록",
            example = "[{\"memberId\":1,\"studyRole\":\"CHALLENGER\"}, {\"memberId\":2,\"studyRole\":\"LEADER\"}]"
    )
    private List<AdminStudyMemberRequest> members;
}
