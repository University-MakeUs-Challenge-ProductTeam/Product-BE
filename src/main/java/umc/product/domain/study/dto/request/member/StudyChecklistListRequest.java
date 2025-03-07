package umc.product.domain.study.dto.request.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "특정 주차 워크북 체크리스트 입력, 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class StudyChecklistListRequest {

    @Schema(description = "체크리스트 요청 리스트", example = "[{\"contentId\": 1, \"checkStatus\": true}, {\"contentId\": 4, \"checkStatus\": true}]")
    private List<StudyChecklistRequest> answers;

    @Schema(description = "특정 주차 워크북 체크리스트 DTO")
    @Getter
    @NoArgsConstructor
    public static class StudyChecklistRequest {

        @Schema(description = "ChecklistContent의 id", example = "1")
        private Long contentId;

        @Schema(description = "체크 상태", example = "true")
        private boolean checkStatus;
    }
}
