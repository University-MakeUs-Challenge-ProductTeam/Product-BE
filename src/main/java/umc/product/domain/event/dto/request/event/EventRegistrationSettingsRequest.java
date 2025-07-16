package umc.product.domain.event.dto.request.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventRegistrationSettingsRequest {

    @Schema(description = "신청 시작 일시", example = "2025-08-01T09:00:00")
    private LocalDateTime registrationStartDate;

    @Schema(description = "신청 종료 일시", example = "2025-08-05T23:59:59")
    private LocalDateTime registrationEndDate;

    @Schema(description = "취소 가능 기한", example = "2025-08-03T23:59:59")
    private LocalDateTime cancellationDeadline;

    @Schema(description = "참여 가능 학기 ID 목록", example = "[1, 2]")
    private List<Long> allowedSemesterIds;

    @Schema(
            description = "참여 가능 파트 목록\n\n" +
                    "- ANDROID\n" +
                    "- IOS\n" +
                    "- SPRING\n" +
                    "- NODE\n" +
                    "- DESIGN\n" +
                    "- WEB\n" +
                    "- PLAN",
            example = "[\"SPRING\", \"WEB\"]"
    )
    private List<Part> allowedPartList;

    @Schema(
            description = "참여 가능 역할 목록\n\n" +
                    "- ADMIN: 관리자\n" +
                    "- CENTRAL_ADMIN: 중앙 운영진\n" +
                    "- SCHOOL_ADMIN: 학교 관리자\n" +
                    "- BRANCH_STAFF: 지부 운영진 (학교 회장, 부회장)\n" +
                    "- UNIVERSITY_STAFF: 교내 운영진 (파트장 등)\n" +
                    "- CHALLENGER: 일반 챌린저\n" +
                    "- GUEST: 비회원",
            example = "[\"CHALLENGER\", \"UNIVERSITY_STAFF\"]"
    )
    private List<Role> allowedRoleList;
}
