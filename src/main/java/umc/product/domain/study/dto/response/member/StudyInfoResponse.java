package umc.product.domain.study.dto.response.member;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "나의 스터디 참여 정보 DTO")
@Getter
@Builder
public class StudyInfoResponse {

    @Schema(description = "스터디 id", example = "5")
    private Long studyId;

    @Schema(description = "기수", example = "5기")
    private String semester;

    @Schema(description = "스터디 타입", example = "교내")
    private String studyType;

    @Schema(description = "파트", example = "Design")
    private String part;

    @Schema(description = "스터디 이름", example = "피그말리온")
    private String studyName;

    @Schema(description = "멤버 닉네임 리스트", example = "[\"너진\",\"더기\",\"제이\"]")
    private List<String> memberList;

    @QueryProjection
    public StudyInfoResponse(Long studyId, String semester, String studyType, String part, String studyName, List<String> memberList) {
        this.studyId = studyId;
        this.semester = semester;
        this.studyType = studyType;
        this.part = part;
        this.studyName = studyName;
        this.memberList = memberList;
    }
}
