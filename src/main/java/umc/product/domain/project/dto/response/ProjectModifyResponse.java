package umc.product.domain.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "프로젝트 수정 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ProjectModifyResponse {

    @Schema(description = "프로젝트 id", example = "1")
    private Long projectId;

    @Schema(description = "프로젝트명", example = "mody")
    private String title;

    @Schema(description = "프로젝트 슬로건", example = "수정된 프로젝트 슬로건")
    private String slogan;

    @Schema(description = "프로젝트 소개글", example = "모드(Mode)와 버디(Buddy)의 결합으로 만들어진 이 이름은, 당신의 라이프스타일과 개성을 반영하는 최적의 '모드'를 발견하도록 돕는 친구를 뜻합니다.")
    private String description;

    @Schema(description = "프로젝트 로고 이미지", example = "https://{bucket-name}.s3.{region}.amazonaws.com/{folder-name}/{file-name}")
    private String logoUrl;

    @Schema(description = "프로젝트 이미지", example = "수정된 프로젝트 이미지")
    private String imgUrl;

    @Schema(description = "개발 기간", example = "2025.xx.xx ~ 2025.xx.xx")
    private String duration;

    @Schema(description = "출시 여부", example = "true")
    private boolean publishStatus;

    @Schema(description = "출시 링크", example = "수정된 출시 링크")
    private String publishLink;

    // todo : University 추가 예정
}
