package umc.product.domain.study.service.member;

import umc.product.domain.member.entity.Member;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.study.dto.response.member.StudyResponse;
import umc.product.domain.study.dto.response.member.StudyWeekChecklistResponse;
import umc.product.domain.study.dto.response.member.StudyWorkbookResponse;
import umc.product.domain.study.dto.response.member.list.StudyListResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface StudyQueryService {

    Study getStudy(Long studyId);
    StudyResponse getStudyResponse(StudyMember studyMember, Roadmap roadmap);
    StudyWorkbookResponse getStudyWorkbookResponse(StudyMember studyMember, int week, List<String> workbookContents, Long loginId);
    StudyWeekChecklistResponse getStudyChecklist(StudyMember studyMember, int week, List<String> roadmapTitleList);
    StudyListResponse getStudyInfoList(Member member);
}
