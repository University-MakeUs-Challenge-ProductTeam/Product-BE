package umc.product.domain.study.service;

import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.study.dto.response.StudyResponse;
import umc.product.domain.study.dto.response.StudyWeekChecklistResponse;
import umc.product.domain.study.dto.response.StudyWorkbookResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface StudyQueryService {

    Study getStudy(Long studyId);
    StudyResponse getStudyResponse(StudyMember studyMember, List<Roadmap> roadmapList);
    StudyWorkbookResponse getStudyWorkbookResponse(StudyMember studyMember, int week, List<String> roadmapTitleList);
    StudyWeekChecklistResponse getStudyChecklist(StudyMember studyMember, int week, List<String> roadmapTitleList);
}
