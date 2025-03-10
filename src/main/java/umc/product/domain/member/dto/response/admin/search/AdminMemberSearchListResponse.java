package umc.product.domain.member.dto.response.admin.search;

import lombok.Builder;
import umc.product.domain.member.dto.response.member.out.MemberOutResponse;
import umc.product.domain.member.dto.response.member.search.MemberSearchResponse;
import umc.product.domain.member.entity.enums.Status;
import umc.product.domain.semester.dto.SemesterPartResponse;
import umc.product.domain.semester.dto.SemesterPositionResponse;

import java.util.List;

@Builder
public record AdminMemberSearchListResponse(
        List<AdminMemberSearchResponse> memberList
){
    @Builder
    public record AdminMemberSearchResponse(

            Long memberId,
            String avatarUrl,
            String name,
            String nickName,
            String university,
            String role,
            Status status,
            String code,
            List<SemesterPartResponse> semesterPartList,
            List<SemesterPositionResponse> semesterPositionList,
            List<MemberOutResponse> memberOutList
    ) {
    }
}
