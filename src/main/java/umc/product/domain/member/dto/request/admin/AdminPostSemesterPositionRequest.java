package umc.product.domain.member.dto.request.admin;

import lombok.Getter;

@Getter
public class AdminPostSemesterPositionRequest {
    private Long semesterId;
    private String position;
}
