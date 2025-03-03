package umc.product.domain.member.dto.request.admin;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Getter
public class AdminPostSemesterPartRequest {
    private Long semesterId;
    private Part part;
}
