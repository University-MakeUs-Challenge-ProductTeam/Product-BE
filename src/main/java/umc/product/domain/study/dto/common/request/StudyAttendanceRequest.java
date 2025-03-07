package umc.product.domain.study.dto.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.study.entity.enums.Check;

@Schema(description = "특정 주차 스터디 참석 여부 체크 요청 DTO")
@Getter
@NoArgsConstructor
public class StudyAttendanceRequest {

    @Schema(description = "참석 여부", example = "YES")
    private String attendance;
}
