package umc.product.domain.member.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.semester.dto.SemesterResponse;
import umc.product.global.common.enums.Status;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class MemberSearchResponse {
    private Long memberId;
    private String avatarUrl;
    private String name;
    private String nickName;
    private String university;
    private String role;
    private Status status;
    private List<SemesterResponse> memberSemesterList;
    private List<MemberPositionResponse> memberPositionList;
}
