package umc.product.domain.study.converter.member;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapTitle;
import umc.product.domain.study.dto.response.member.*;
import umc.product.domain.study.dto.response.member.list.StudyListResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudyConverter {

    public StudyResponse toStudyResponse(StudyMember studyMember, List<StudyMemberResponse> studyMemberResponseList, List<Roadmap> roadmapList) {
        Study study = studyMember.getStudy();

        List<StudyResponse.StudyRoadmapResponse> roadmapResponseList = roadmapList.stream()
                .map(this::toStudyRoadmapResponse)
                .collect(Collectors.toList());

        List<String> roadmapTitleList = roadmapResponseList.stream()
                .filter(rr -> rr.getWeek() == study.getCurrentWeek())
                .flatMap(rr -> rr.getTitles().stream())
                .collect(Collectors.toList());

        return StudyResponse.builder()
                .semester(studyMember.getSemesterPart().getSemester().getName())
                .part(studyMember.getSemesterPart().getPart().toString())
                .studyName(study.getName())
                .roadmapTitles(roadmapTitleList)
                .members(studyMemberResponseList)
                .roadmaps(roadmapResponseList)
                .build();
    }

    public StudyResponse.StudyRoadmapResponse toStudyRoadmapResponse(Roadmap roadmap) {
        List<String> titles = roadmap.getRoadmapTitleList().stream()
                .map(RoadmapTitle::getTitle)
                .collect(Collectors.toList());
        return StudyResponse.StudyRoadmapResponse.builder()
                .week(roadmap.getWeek())
                .titles(titles)
                .build();
    }

    public StudyWorkbookResponse toStudyWorkbookResponse(StudyMember studyMember, List<StudyMemberResponse> studyMemberResponseList, int week,
                                                         List<String> roadmapTitleList, List<StudyWorkbookResponse.StudyChecklistResponse> studyChecklists) {
        Member member = studyMember.getSemesterPart().getMember();

        return StudyWorkbookResponse.builder()
                .nickName(member.getNickName())
                .week(week)
                .workbookContents(roadmapTitleList)
                .members(studyMemberResponseList)
                .checklists(studyChecklists)
                .build();
    }

    public StudyWeekChecklistResponse toStudyWeekCheckListResponse(int week, List<String> roadmapTitleList,
                                                                   List<StudyWeekChecklistResponse.ChecklistResponse> checklistResponseList) {
        return StudyWeekChecklistResponse.builder()
                .week(week)
                .workbookContents(roadmapTitleList)
                .checklists(checklistResponseList)
                .build();
    }

    public StudyListResponse toStudyListResponse(List<StudyInfoResponse> studyInfoResponseList) {
        return StudyListResponse.builder()
                .studyResponseList(studyInfoResponseList)
                .build();
    }

}
