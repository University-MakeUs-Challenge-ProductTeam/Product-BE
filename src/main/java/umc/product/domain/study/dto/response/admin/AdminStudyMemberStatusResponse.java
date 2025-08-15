package umc.product.domain.study.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AdminStudyMemberStatusResponse {
  private List<MemberStatusInfo> members;


  @Getter
  @Builder
  @AllArgsConstructor
  public static class MemberStatusInfo {

    @Schema(description = "스터디 멤버 ID")
    private Long studyMemberId;

    @Schema(description = "멤버 실명", example = "오정현")
    private String memberName;

    // 1. nickname 필드 추가
    @Schema(description = "멤버 닉네임", example = "델로")
    private String nickname;

    @Schema(description = "파트", example = "SPRING")
    private String part;

    // 2. Enum의 name("스터디 리더", "스터디원")이 들어갈 필드
    @Schema(description = "스터디 내 역할", example = "스터디 리더")
    private String roleInStudy;

    // 3. 필드명 및 타입명 변경
    @Schema(description = "주차별 체크리스트 상태 목록")
    private List<WeeklyChecklistStatus> weeklyChecklistStatuses;
  }


  // 3. 클래스명 변경
  @Getter
  @Builder
  @AllArgsConstructor
  public static class WeeklyChecklistStatus {

    @Schema(description = "주차", example = "1")
    private int week;

    @Schema(description = "해당 주차의 체크리스트 종합 상태", example = "YES")
    private String status;
  }

}
