package umc.product.domain.member.dto.response.member.search;

import lombok.Builder;

@Builder
public record MemberSemesterPositionResponse(

        Long positionId,
        String semesterName,
        String position
){

}
