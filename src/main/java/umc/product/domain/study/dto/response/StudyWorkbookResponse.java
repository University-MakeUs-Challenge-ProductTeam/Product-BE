package umc.product.domain.study.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "나의 주차별 워크북 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class StudyWorkbookResponse {

    @Schema(description = "닉네임", example = "델로")
    private String nickname;

    @Schema(description = "주차", example = "1")
    private int week;

    @Schema(description = "워크북 내용", example = "[\"Figma 설치\", \"Figma의 이해\"]")
    private List<String> workbookContents;

    @Schema(description = "스터디 멤버 목록")
    private List<StudyMemberResponse> members;

    @Schema(description = "체크리스트 목록")
    private List<StudyChecklistResponse> checklists;

    @Schema(description = "체크리스트 정보")
    @Getter
    @Builder
    public static class StudyChecklistResponse {

        @Schema(description = "체크리스트 제목", example = "참석")
        private String title;

        @Schema(description = "체크리스트 상태", example = "YES")
        private String status;

        @QueryProjection
        public StudyChecklistResponse(String title, String status) {
            this.title = title;
            this.status = status;
        }
    }
}
