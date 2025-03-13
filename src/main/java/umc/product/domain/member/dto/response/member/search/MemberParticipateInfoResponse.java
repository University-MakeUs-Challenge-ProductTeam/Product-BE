package umc.product.domain.member.dto.response.member.search;

import lombok.Builder;
import umc.product.domain.member.entity.enums.Part;

import java.util.List;

@Builder
public record MemberParticipateInfoResponse(
        Long memberId,
        List<MemberSemesterInfoResponse> semesterList
) {
    @Builder
    public record MemberSemesterInfoResponse(
            Long semesterId,
            String semester,
            Part part,
            String universityPosition,
            String centralPosition
    ) {

    }
}
