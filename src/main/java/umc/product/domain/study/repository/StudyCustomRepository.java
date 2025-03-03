package umc.product.domain.study.repository;

import umc.product.domain.study.dto.response.StudyMemberResponse;
import umc.product.domain.study.entity.Study;

import java.util.List;

public interface StudyCustomRepository {

    List<StudyMemberResponse> getStudyMembers(Study study);
}
