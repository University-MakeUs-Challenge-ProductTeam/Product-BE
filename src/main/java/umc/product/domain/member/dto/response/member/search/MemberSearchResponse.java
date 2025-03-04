package umc.product.domain.member.dto.response.member.search;

import lombok.Builder;
import umc.product.domain.semester.dto.SemesterPartResponse;
import umc.product.domain.member.entity.enums.Status;

import java.util.List;

@Builder
public record MemberSearchResponse(

        Long memberId,
        String avatarUrl,
        String name,
        String nickName,
        String university,
        String role,
        Status status,
        List<SemesterPartResponse> memberSemesterPartList,
        List<MemberSemesterPositionResponse> memberSemesterPositionList,
        List<MemberOutResponse> memberOutList
) {
}
