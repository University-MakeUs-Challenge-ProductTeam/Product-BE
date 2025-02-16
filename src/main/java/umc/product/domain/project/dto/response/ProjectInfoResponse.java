package umc.product.domain.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "내 프로젝트에 대한 정보 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ProjectInfoResponse {

    @Schema(description = "프로젝트 id", example = "1")
    private Long projectId;

    @Schema(description = "프로젝트명", example = "mody")
    private String title;

    @Schema(description = "프로젝트 슬로건", example = "체형과 취향을 고려한 AI 기반 맞춤형 스타일링 컨설팅 서비스")
    private String slogan;

    @Schema(description = "프로젝트 소개글", example = "모드(Mode)와 버디(Buddy)의 결합으로 만들어진 이 이름은, 당신의 라이프스타일과 개성을 반영하는 최적의 '모드'를 발견하도록 돕는 친구를 뜻합니다.")
    private String description;

    @Schema(description = "프로젝트 로고 이미지", example = "https://{bucket-name}.s3.{region}.amazonaws.com/{folder-name}/{file-name}")
    private String logoUrl;

    @Schema(description = "프로젝트 이미지", example = "https://{bucket-name}.s3.{region}.amazonaws.com/{folder-name}/{file-name}")
    private String imgUrl;

    @Schema(description = "기수", example = "7기")
    private String semester;

    // todo : university 추가 예정

    @Schema(description = "개발 기간", example = "2025.xx.xx ~ 2025.xx.xx")
    private String duration;

    @Schema(description = "출시 여부", example = "true")
    private boolean publishStatus;

    @Schema(description = "출시 링크", example = "https://{domain-name}")
    private String publishLink;
}
