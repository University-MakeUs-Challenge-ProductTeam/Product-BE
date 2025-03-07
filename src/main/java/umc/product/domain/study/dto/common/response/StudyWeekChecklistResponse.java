package umc.product.domain.study.dto.common.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "특정 주차 체크리스트 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class StudyWeekChecklistResponse {

    @Schema(description = "주차", example = "1")
    private int week;

    @Schema(description = "워크북 내용", example = "[\"Figma 설치\", \"Figma의 이해\"]")
    private List<String> workbookContents;

    @Schema(description = "체크리스트 목록")
    private List<ChecklistResponse> checklists;

    @Schema(description = "체크리스트 응답 DTO")
    @Getter
    @Builder
    public static class ChecklistResponse {

        @Schema(description = "체크리스트 타입", example = "SELECT")
        private String type;

        @Schema(description = "체크리스트 제목", example = "스터디에 참석하셨나요?")
        private String title;

        @Schema(description = "체크리스트 내용 목록")
        private List<ChecklistContentResponse> contents;

        @QueryProjection
        public ChecklistResponse(String type, String title, List<ChecklistContentResponse> contents) {
            this.type = type;
            this.title = title;
            this.contents = contents;
        }
    }

    @Schema(description = "체크리스트 내용 응답 DTO")
    @Getter
    @Builder
    public static class ChecklistContentResponse {

        @Schema(description = "체크리스트 contentId", example = "101")
        private Long contentId;

        @Schema(description = "체크리스트 content", example = "네, 참석했어요")
        private String content;

        @Schema(name = "check_status", description = "체크 상태", example = "true")
        private boolean checkStatus;

        @QueryProjection
        public ChecklistContentResponse(Long contentId, String content, boolean checkStatus) {
            this.contentId = contentId;
            this.content = content;
            this.checkStatus = checkStatus;
        }
    }
}
