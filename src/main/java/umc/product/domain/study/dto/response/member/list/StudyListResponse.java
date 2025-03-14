package umc.product.domain.study.dto.response.member.list;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.study.dto.response.member.StudyInfoResponse;

import java.util.List;

@Schema(description = "나의 스터디 참여 목록 조회 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class StudyListResponse {

    @Schema(description = "스터디 참여 리스트")
    private final List<StudyInfoResponse> studyResponseList;
}
