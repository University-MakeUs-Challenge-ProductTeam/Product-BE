package umc.product.domain.checklist.service;

import umc.product.domain.study.dto.request.StudyChecklistListRequest;
import umc.product.domain.study.dto.response.StudyCommonResponse;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface ChecklistCommandService {

    StudyCommonResponse updateChecklistAnswers(StudyMember studyMember, int week, List<StudyChecklistListRequest.StudyChecklistRequest> answers);
}
