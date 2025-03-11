package umc.product.domain.member.dto.request.admin.member;

import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.member.entity.enums.Status;

import java.util.List;

public record AdminUpdateMemberProfileRequest(
        String name,
        String nickName,
        String universityName,
        Status status,
        List<AdminUpdateSemesterPositionRequest> semesterPositionList,
        List<AdminUpdateSemesterPartRequest> semesterPartList
){
    public record AdminUpdateSemesterPartRequest(
            Long semesterPartId,
            Long semesterId,
            Part part
    ) {

    }

    public record AdminUpdateSemesterPositionRequest(
            Long semesterPositionId,
            Long semesterId,
            String position
    ){

    }

}
