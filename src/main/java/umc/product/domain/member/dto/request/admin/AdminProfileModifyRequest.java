package umc.product.domain.member.dto.request.admin;

import lombok.Getter;
import umc.product.domain.member.entity.enums.Status;

import java.util.List;

@Getter
public class AdminProfileModifyRequest {
    private String name;
    private String nickName;
    private String universityName;
    private Status status;
    private List<AdminSemesterPositionRequest> semesterPositionList;
    private List<AdminSemesterPartRequest> semesterPartList;
}
