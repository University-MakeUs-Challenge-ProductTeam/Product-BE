package umc.product.domain.semester.dto;

import lombok.Builder;

@Builder
public record SemesterPositionResponse(

        Long positionId,
        String semesterName,
        String universityPosition,
        String centralPosition
){

}
