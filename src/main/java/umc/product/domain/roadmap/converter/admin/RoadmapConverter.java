package umc.product.domain.roadmap.converter.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.checklist.entity.Checklist;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.roadmap.dto.response.admin.AdminRoadmapResponse;
import umc.product.domain.roadmap.dto.response.admin.RoadmapDetailResponse;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapWeek;

@Component
@RequiredArgsConstructor
public class RoadmapConverter {

  public AdminRoadmapResponse toAdminRoadmapResponse(Roadmap roadmap) {
    // 1. RoadmapWeek 리스트를 '주차(week)'별로 그룹핑하고
    //    각 그룹에 속한 subject들만 모아서 Map<Integer, List<String>> 형태로 변환
    Map<Integer, List<String>> subjectsPerWeek = roadmap.getRoadmapWeekList().stream()
        .collect(Collectors.groupingBy(
            RoadmapWeek::getWeek,
            Collectors.mapping(RoadmapWeek::getSubject, Collectors.toList())
        ));

    // 2. 위에서 만든 Map을 DTO 형태인 List<WeeklySubjectResponse>로 다시 변환
    List<AdminRoadmapResponse.WeeklySubjectResponse> weeklySubjectsResponse = subjectsPerWeek.entrySet().stream()
        .map(entry -> AdminRoadmapResponse.WeeklySubjectResponse.builder()
            .week(entry.getKey())
            .subjects(entry.getValue())
            .build())
        .sorted(Comparator.comparingInt(AdminRoadmapResponse.WeeklySubjectResponse::getWeek)) // 주차 순서대로 정렬
        .collect(Collectors.toList());

    // 3. 최종 DTO를 조립하여 반환합니다.
    return AdminRoadmapResponse.builder()
        .roadmapId(roadmap.getId())
        .title(roadmap.getTitle())
        .part(roadmap.getPart())
        .weeklySubjects(weeklySubjectsResponse)
        .build();
  }

  public RoadmapDetailResponse toRoadmapDetailResponse(Roadmap roadmap, List<RoadmapWeek> allWeeks, List<Checklist> allChecklists) {
    Map<Integer, List<String>> subjectsByWeek = allWeeks.stream()
        .collect(Collectors.groupingBy(
            RoadmapWeek::getWeek,
            Collectors.mapping(RoadmapWeek::getSubject, Collectors.toList())
        ));

    Map<Integer, List<Checklist>> checklistsByWeek = allChecklists.stream()
        .collect(Collectors.groupingBy(Checklist::getWeek));

    int maxWeek = Math.max(
        subjectsByWeek.keySet().stream().max(Integer::compareTo).orElse(0),
        checklistsByWeek.keySet().stream().max(Integer::compareTo).orElse(0)
    );

    List<RoadmapDetailResponse.WeeklyDetail> weeklyDetails = IntStream.rangeClosed(1, maxWeek)
        .mapToObj(week -> {
          List<String> subjects = subjectsByWeek.getOrDefault(week, Collections.emptyList());
          List<Checklist> checklists = checklistsByWeek.getOrDefault(week, Collections.emptyList());

          List<RoadmapDetailResponse.ChecklistResponse> checklistResponses = checklists.stream()
              .map(checklist -> RoadmapDetailResponse.ChecklistResponse.builder()
                  .category(checklist.getChecklistCategory().name())
                  .title(checklist.getTitle())
                  .contents(checklist.getChecklistContentList().stream()
                      .map(ChecklistContent::getContent)
                      .collect(Collectors.toList()))
                  .build())
              .collect(Collectors.toList());

          return RoadmapDetailResponse.WeeklyDetail.builder()
              .week(week)
              .subjects(subjects)
              .checklists(checklistResponses)
              .build();
        })
        .collect(Collectors.toList());

    return RoadmapDetailResponse.builder()
        .roadmapTitle(roadmap.getTitle())
        .part(roadmap.getPart().name())
        .weeklyDetails(weeklyDetails)
        .build();
  }
}
