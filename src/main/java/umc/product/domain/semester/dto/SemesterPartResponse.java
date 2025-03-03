package umc.product.domain.semester.dto;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Getter
@Builder
public class SemesterPartResponse {
    private Long semesterPartId;
    private String semester;
    private Part part;
}
