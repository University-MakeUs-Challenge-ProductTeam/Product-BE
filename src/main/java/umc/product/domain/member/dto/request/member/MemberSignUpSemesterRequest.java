package umc.product.domain.member.dto.request.member;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Getter
public class MemberSignUpSemesterRequest {
    private Long semesterId;
    private Part part;
}
