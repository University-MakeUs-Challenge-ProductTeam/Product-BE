package umc.product.domain.project.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.enums.Prize;

@Schema(description = "프로젝트 목록 조회 응답 DTO")
@Getter
@Builder
public class ProjectResponse {

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

    @QueryProjection
    public ProjectResponse(Long projectId, String title, String slogan, String logoUrl, Prize prize, Part frontPart, Part serverPart, int participantCount) {
        this.projectId = projectId;
        this.title = title;
        this.slogan = slogan;
        this.logoUrl = logoUrl;
        this.prize = prize;
        this.frontPart = frontPart;
        this.serverPart = serverPart;
        this.participantCount = participantCount;
    }
}
