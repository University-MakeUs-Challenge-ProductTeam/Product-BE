package umc.product.domain.member.dto.request.admin;

import lombok.Getter;

import java.util.List;

@Getter
public class AdminPostPositionRequest {
    private List<AdminPostSemesterPositionRequest> semesterPositionList;
}
