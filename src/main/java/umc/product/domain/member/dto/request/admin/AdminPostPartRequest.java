package umc.product.domain.member.dto.request.admin;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Status;

import java.util.List;

@Getter
public class AdminPostPartRequest {
    private List<AdminPostSemesterPartRequest> semesterPartList;
}
