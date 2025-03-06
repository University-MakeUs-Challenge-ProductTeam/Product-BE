package umc.product.domain.member.dto.request.admin.member;


import java.util.List;

public record AdminInsertSemesterPositionListRequest(
        List<AdminInsertSemesterPositionRequest> semesterPositionList
){
}
