package umc.product.domain.semester.dto;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Builder
public record SemesterPartResponse (
        Long semesterPartId,
        String semester,
        Part part
){
}
