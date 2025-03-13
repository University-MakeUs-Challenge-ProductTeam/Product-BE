package umc.product.domain.member.dto.request.admin.member;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public record AdminInsertSemesterPositionListRequest(
        List<AdminInsertSemesterPositionRequest> semesterPositionList
){
    public record AdminInsertSemesterPositionRequest(
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
