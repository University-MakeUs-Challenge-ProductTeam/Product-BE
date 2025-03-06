package umc.product.domain.study.repository;

import umc.product.domain.study.dto.response.StudyMemberResponse;
import umc.product.domain.study.dto.response.StudyWeekChecklistResponse;
import umc.product.domain.study.dto.response.StudyWorkbookResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface StudyCustomRepository {

    List<StudyMemberResponse> getStudyMembers(Study study);
    List<StudyWorkbookResponse.StudyChecklistResponse> getStudyChecklists(Long studyMemberId, int week);
    List<StudyWeekChecklistResponse.ChecklistResponse> getChecklistResponses(StudyMember studyMember, int week);

}
