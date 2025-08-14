package umc.product.domain.study.converter.member;

import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapWeek;
import umc.product.domain.study.dto.response.member.*;
import umc.product.domain.study.dto.response.member.list.StudyListResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudyConverter {

    public StudyResponse toStudyResponse(StudyMember studyMember, List<StudyMemberResponse> studyMemberResponseList, Roadmap roadmap) {
        Study study = studyMember.getStudy();

        List<StudyResponse.WeeklyRoadmapResponse> weeklyRoadmaps = roadmap.getRoadmapWeekList().stream()
            .map(roadmapWeek -> StudyResponse.WeeklyRoadmapResponse.builder()
                .week(roadmapWeek.getWeek())
                .subjects(List.of(roadmapWeek.getSubject()))
                .build())
            .collect(Collectors.toList());

        // 현재 주차의 주제 목록
        List<String> currentWeekSubjects = roadmap.getRoadmapWeekList().stream() // <- 수정된 부분
            .filter(roadmapWeek -> roadmapWeek.getWeek() == study.getCurrentWeek())
            .map(RoadmapWeek::getSubject)
            .collect(Collectors.toList());

        return StudyResponse.builder()
            .studyId(study.getId())
            .currentWeek(study.getCurrentWeek())
            .semester(studyMember.getSemesterPart().getSemester().getName())
            .part(studyMember.getSemesterPart().getPart().toString())
            .studyName(study.getName())
            .currentWeekSubjects(currentWeekSubjects)
            .members(studyMemberResponseList)
            .weeklyRoadmaps(weeklyRoadmaps)
            .build();
    }

    public StudyWorkbookResponse toStudyWorkbookResponse(StudyMember studyMember, List<StudyMemberResponse> studyMemberResponseList, int week,
                                                         List<String> workbookContents, List<StudyWorkbookResponse.StudyChecklistResponse> studyChecklists) {
        Member member = studyMember.getSemesterPart().getMember();

        return StudyWorkbookResponse.builder()
                .nickName(member.getNickName())
                .week(week)
                .workbookContents(workbookContents)
                .members(studyMemberResponseList)
                .checklists(studyChecklists)
                .build();
    }

    public StudyWeekChecklistResponse toStudyWeekCheckListResponse(int week, List<String> workbookContents, boolean postStatus,
                                                                   List<StudyWeekChecklistResponse.ChecklistResponse> checklistResponseList) {
        return StudyWeekChecklistResponse.builder()
                .week(week)
                .postStatus(postStatus)
                .workbookContents(workbookContents)
                .checklists(checklistResponseList)
                .build();
    }

    public StudyListResponse toStudyListResponse(List<StudyInfoResponse> studyInfoResponseList) {
        return StudyListResponse.builder()
                .studyResponseList(studyInfoResponseList)
                .build();
    }

}
