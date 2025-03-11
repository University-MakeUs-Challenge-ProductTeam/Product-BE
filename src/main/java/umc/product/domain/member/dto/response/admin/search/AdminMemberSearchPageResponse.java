package umc.product.domain.member.dto.response.admin.search;

import lombok.Builder;
import org.springframework.data.domain.Page;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.semester.dto.SemesterPartResponse;
import umc.product.domain.semester.dto.SemesterPositionResponse;

import java.util.List;

@Builder
public record AdminMemberSearchPageResponse(
        Page<AdminMemberSearchResponse> memberList
){
    @Builder
    public record AdminMemberSearchResponse(

            Long memberId,
            String avatarUrl,
            String name,
            String nickName,
            String universityName,
            String code,
            Status status,
            List<SemesterPartResponse> semesterPartList,
            List<SemesterPositionResponse> semesterPositionList,
            Integer outCount
    ) {
    }
}
