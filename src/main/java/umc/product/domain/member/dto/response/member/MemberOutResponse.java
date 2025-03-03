package umc.product.domain.member.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.OutReason;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.semester.dto.SemesterPartResponse;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberOutResponse {
    private Long outId;
    private String reason;
}
