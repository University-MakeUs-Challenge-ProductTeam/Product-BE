package umc.product.domain.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "프로젝트 수정 요청 DTO")
@Getter
@NoArgsConstructor
public class ProjectModifyRequest {

    @Schema(description = "프로젝트명", example = "null")
    private String title;

    @Schema(description = "프로젝트 슬로건", example = "수정된 프로젝트 슬로건")
    private String slogan;

    @Schema(description = "프로젝트 소개글", example = "null")
    private String description;

    @Schema(description = "프로젝트 로고 이미지", example = "null")
    private String logoUrl;

    @Schema(description = "프로젝트 이미지", example = "수정된 프로젝트 이미지")
    private String imgUrl;

    @Schema(description = "개발 기간", example = "null")
    private String duration;

    @Schema(description = "출시 여부", example = "true")
    private boolean publishStatus;

    @Schema(description = "출시 링크", example = "수정된 출시 링크")
    private String publishLink;

    // todo : University 추가 예정
}
