package umc.product.domain.study.dto.request.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "스터디 생성 요청 DTO")
@Getter
@NoArgsConstructor
public class AdminStudyRequest {

    @Schema(description = "스터디 타입", example = "SCHOOL")
    private String studyType;

    @Schema(description = "파트", example = "SPRING")
    private String part;

    @Schema(description = "기수 id", example = "8")
    private Long semesterId;

    @Schema(description = "스터디 임시 이름", example = "임시 스터디명")
    private String studyName;

    @Schema(description = "스터디 현재 주차", example = "0")
    private int currentWeek;

    @Schema(
            description = "스터디 참여 사용자 목록",
            example = "[{\"memberId\":1,\"studyRole\":\"LEADER\"}, {\"memberId\":2,\"studyRole\":\"CHALLENGER\"}]"
    )
    private List<AdminStudyMemberRequest> members;
}
