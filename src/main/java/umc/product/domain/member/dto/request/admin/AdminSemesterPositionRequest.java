package umc.product.domain.member.dto.request.admin;

import lombok.Getter;

@Getter
public class AdminSemesterPositionRequest {
    private Long semesterPositionId;
    private Long semesterId;
    private String position;
}
