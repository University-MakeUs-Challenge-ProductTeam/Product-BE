package umc.product.domain.member.dto.request.admin;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Getter
public class AdminSemesterPartRequest {
    private Long semesterPartId;
    private Long semesterId;
    private Part part;
}
