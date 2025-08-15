package umc.product.domain.study.converter.member;

import java.util.Set;
import org.springframework.stereotype.Component;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.checklist.entity.ChecklistMemberAnswer;
import umc.product.domain.member.entity.Member;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapWeek;
import umc.product.domain.study.dto.response.admin.MemberWorkbookResponse;
import umc.product.domain.study.dto.response.member.*;
import umc.product.domain.study.dto.response.member.list.StudyListResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;
import java.util.stream.Collectors;
import umc.product.domain.study.entity.WeeklyStudyStatus;
import umc.product.domain.study.entity.enums.PassStatus;

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

    public MemberWorkbookResponse toMemberWorkbookResponse(StudyMember studyMember, int week, WeeklyStudyStatus weeklyStudyStatus, List<RoadmapWeek> roadmapWeeks, List<Checklist> checklists, List<ChecklistMemberAnswer> memberAnswers) {
        // 이 멤버가 체크한 ChecklistContent의 ID만 Set으로 만들어 빠른 조회를 돕습니다.
        Set<Long> checkedContentIds = memberAnswers.stream()
            .filter(ChecklistMemberAnswer::isCheckStatus)
            .map(answer -> answer.getChecklistContent().getId())
            .collect(Collectors.toSet());

        // Checklist 엔티티 목록을 DTO 목록으로 변환합니다.
        List<MemberWorkbookResponse.ChecklistDetail> checklistDetails = checklists.stream()
            .map(checklist -> {
                List<MemberWorkbookResponse.ChecklistContentDetail> contentDetails = checklist.getChecklistContentList().stream()
                    .map(content -> MemberWorkbookResponse.ChecklistContentDetail.builder()
                        .contentId(content.getId())
                        .content(content.getContent())
                        .isChecked(checkedContentIds.contains(content.getId())) // Set을 이용해 체크 여부 확인
                        .build())
                    .toList();

                return MemberWorkbookResponse.ChecklistDetail.builder()
                    .checklistId(checklist.getId())
                    .category(checklist.getChecklistCategory().name())
                    .title(checklist.getTitle())
                    .contents(contentDetails)
                    .build();
            }).toList();

        // 최종 DTO를 조립하여 반환합니다.
        return MemberWorkbookResponse.builder()
            .nickname(studyMember.getSemesterPart().getMember().getNickName())
            .week(week)
            .weeklyPassStatus(weeklyStudyStatus != null ? weeklyStudyStatus.getStatus().name() : PassStatus.PENDING.name())
            .subjects(roadmapWeeks.stream().map(RoadmapWeek::getSubject).toList())
            .checklists(checklistDetails)
            .build();

    }
}
