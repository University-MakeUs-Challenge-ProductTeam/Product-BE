package umc.product.domain.semester.dto;

import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

import java.util.List;

@Getter
@Builder
public class SemesterResponse {
    private String semester;
    private Part part;
}
