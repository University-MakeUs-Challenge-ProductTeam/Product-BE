package umc.product.domain.study.dto.response.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Schema(description = "나의 스터디 정보 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class StudyResponse {

    @Schema(description = "스터디 id", example = "1")
    private Long studyId;

    @Schema(description = "현재 주차", example = "0")
    private int currentWeek;

    @Schema(description = "학기 정보", example = "5기")
    private String semester;

    @Schema(description = "파트 정보", example = "DESIGN")
    private String part;

    @Schema(description = "스터디 이름", example = "피그말리온")
    private String studyName;

    @Schema(description = "로드맵 타이틀 목록", example = "[\"Figma 설치\", \"Figma의 이해\"]")
    private List<String> roadmapTitles;

    @Schema(description = "스터디 멤버 목록")
    private List<StudyMemberResponse> members;

    @Schema(description = "주차별 로드맵 정보")
    private List<StudyRoadmapResponse> roadmaps;


    @Schema(description = "주차별 로드맵 정보")
    @Getter
    @Builder
    @AllArgsConstructor
    public static class StudyRoadmapResponse {

        @Schema(description = "주차", example = "1")
        private int week;

        @Schema(description = "해당 주차 타이틀 목록", example = "[\"Figma 설치\", \"Figma의 이해\"]")
        private List<String> titles;
    }
}
