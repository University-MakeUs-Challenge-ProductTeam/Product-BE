package umc.product.domain.member.dto.request.admin.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
            String centralPosition,
            String universityPosition
    ){
        @JsonIgnore
        public boolean isValid() {
            return (centralPosition != null && universityPosition == null) || (centralPosition == null && universityPosition != null);
        }
    }

}
