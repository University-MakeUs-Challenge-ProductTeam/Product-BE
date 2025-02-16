package umc.product.domain.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.enums.Prize;
import umc.product.global.dto.response.CursorPagination;

import java.util.List;

@Schema(description = "프로젝트 목록 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ProjectListResponse {

    @Schema(description = "프로젝트 목록 리스트")
    private final List<ProjectResponse> projectResponses;

//    @Schema(description = "커서 기반 페이지네이션")
//    private final CursorPagination cursorPagination;

    @Schema(description = "프로젝트 기본 정보 DTO")
    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProjectResponse {

        @Schema(description = "프로젝트 id", example = "1")
        private Long projectId;

        @Schema(description = "프로젝트명", example = "mody")
        private String title;

        @Schema(description = "프로젝트 슬로건", example = "체형과 취향을 고려한 AI 기반 맞춤형 스타일링 컨설팅 서비스")
        private String slogan;

        @Schema(description = "프로젝트 로고 이미지", example = "https://{bucket-name}.s3.{region}.amazonaws.com/{folder-name}/{file-name}")
        private String logoUrl;

        @Schema(description = "수상 여부", example = "FIRST")
        private Prize prize;

        @Schema(description = "WEB / IOS / ANDROID 여부", example = "WEB")
        private Part frontPart;

        @Schema(description = "SPRING / NODE 여부", example = "SPRING")
        private Part serverPart;

        @Schema(description = "참여 인원수", example = "12")
        private int participantCount;
    }
}
