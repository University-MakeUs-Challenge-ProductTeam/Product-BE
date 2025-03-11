package umc.product.domain.member.dto.response.member.search;

import lombok.Builder;
import umc.product.domain.semester.dto.SemesterPartResponse;
import umc.product.domain.semester.dto.SemesterPositionResponse;

import java.util.List;

@Builder
public record MemberProfileDetailResponse(

        Long memberId,
        String avatarUrl,
        String name,
        String nickName,
        String university,
        List<SemesterPartResponse> semesterPartList,
        List<SemesterPositionResponse> semesterPositionList
) {
}
