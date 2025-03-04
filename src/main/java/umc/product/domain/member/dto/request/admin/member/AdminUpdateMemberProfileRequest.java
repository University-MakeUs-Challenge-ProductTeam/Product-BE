package umc.product.domain.member.dto.request.admin.member;

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

}
