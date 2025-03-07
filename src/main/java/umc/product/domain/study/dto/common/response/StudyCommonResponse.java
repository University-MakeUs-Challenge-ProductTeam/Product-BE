package umc.product.domain.study.dto.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "스터디 관련 생성, 수정, 삭제 시 공통 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class StudyCommonResponse {

    @Schema(description = "스터디 id", example = "1")
    private Long studyId;

    public static StudyCommonResponse from(Long studyId){
        return StudyCommonResponse.builder()
                .studyId(studyId)
                .build();
    }
}
