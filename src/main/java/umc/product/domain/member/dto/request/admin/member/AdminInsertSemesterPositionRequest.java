package umc.product.domain.member.dto.request.admin.member;

public record AdminInsertSemesterPositionRequest(
        Long semesterId,
        String position
){

}
