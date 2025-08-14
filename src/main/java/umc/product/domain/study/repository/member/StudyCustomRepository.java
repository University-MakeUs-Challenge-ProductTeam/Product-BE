package umc.product.domain.study.repository.member;

import umc.product.domain.member.entity.Member;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.study.dto.response.member.StudyInfoResponse;
import umc.product.domain.study.dto.response.member.StudyMemberResponse;
import umc.product.domain.study.dto.response.member.StudyWeekChecklistResponse;
import umc.product.domain.study.dto.response.member.StudyWorkbookResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface StudyCustomRepository {

    List<StudyMemberResponse> getStudyMembers(Study study);
    List<StudyWorkbookResponse.StudyChecklistResponse> getStudyChecklists(Long studyMemberId, RoadmapSemester roadmapSemester, int week);
    List<StudyWeekChecklistResponse.ChecklistResponse> getChecklistResponses(Long studyMemberId, RoadmapSemester roadmapSemester, int week);
    boolean getPostStatus(StudyMember studyMember, int week);
    List<StudyInfoResponse> getStudyInfoList(Member loginMember);

}
