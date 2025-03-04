package umc.product.domain.member.dto.request.admin.member;

public record AdminUpdateSemesterPositionRequest(
        Long semesterPositionId,
        Long semesterId,
        String position
){

}
