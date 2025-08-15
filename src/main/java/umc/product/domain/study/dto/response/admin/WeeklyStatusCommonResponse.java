package umc.product.domain.study.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "주차별 스터디 상태 처리 완료 응답 DTO")
public class WeeklyStatusCommonResponse {

  @Schema(description = "처리(생성 또는 수정)된 WeeklyStudyStatus의 ID")
  private Long weeklyStatusId;

}
