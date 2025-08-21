package umc.product.domain.event.dto.request.event;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.event.entity.event.EventType;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Role;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventUpdateRequest {

    @Schema(description = "행사 제목", example = "8기 데모데이")
    @NotBlank(message = "행사 제목은 필수 입력값입니다.")
    private String title;

    @Schema(description = "행사 내용", example = "8기 데모데이를 개최 합니다.")
    @NotBlank(message = "행사 내용은 필수 입력값입니다.")
    private String content;

    @Schema(
            description = "행사 타입 (UNIVERSITY, DEPARTMENT, CENTRAL 중 택 1)",
            example = "UNIVERSITY"
    )
    @NotBlank(message = "행사 타입은 필수 입력값입니다.")
    private EventType eventType;

    @Schema(description = "학기 아이디", example = "1")
    private Long semesterId;

    @Schema(description = "행사 일자", example = "2025-07-03")
    @Future(message = "행사 일자의 경우, 과거 날짜를 선택할 수 없습니다.")
    private LocalDate eventDate;

    @Schema(description = "행사 소요 시간", example = "02:30:00")
    private LocalTime eventTime;

    @Schema(description = "행사 장소", example = "프론트원")
    @NotBlank(message = "행사 장소는 필수 입력값입니다.")
    private String location;

    @Schema(description = "참여 최대 인원", example = "120")
    private Integer maxParticipants;

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

    @Schema(description = "기존 이미지 URL 목록", example = "[\"s3 url1\", \"s3 url2\"]")
    private List<String> existingImages;
}
