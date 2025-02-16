package umc.product.domain.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.project.enums.Phase;

@Schema(description = "프로젝트 진행 상황 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class ProjectProgressResponse {

    @Schema(description = "프로젝트명", example = "mody")
    private String title;

    @Schema(description = "프로젝트 로고 이미지", example = "https://{bucket-name}.s3.{region}.amazonaws.com/{folder-name}/{file-name}")
    private String logoUrl;

    @Schema(description = "파트", example = "SPRING")
    private Part part;

    @Schema(description = "과제 차수(1차, 2차, 3차)", example = "FIRST")
    private Phase phase;

    @Schema(description = "과제 내용", example = "SPRING 1차 과제 내용")
    private String content;
}
