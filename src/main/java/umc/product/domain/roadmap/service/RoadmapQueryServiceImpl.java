package umc.product.domain.roadmap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapWeek;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.repository.RoadmapWeekRepository;
import umc.product.domain.roadmap.status.RoadmapErrorStatus;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoadmapQueryServiceImpl implements RoadmapQueryService {

    private final RoadmapRepository roadmapRepository;
    private final RoadmapWeekRepository roadmapWeekRepository;

    @Override
    public Roadmap getRoadmap(StudyMember studyMember) {

        SemesterPart semesterPart = studyMember.getSemesterPart();
        Long semesterId = semesterPart.getSemester().getId();
        Part part = semesterPart.getPart();

        Roadmap roadmap = roadmapRepository.findBySemesterIdAndPart(semesterId, part)
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_NOT_FOUND));

        // 3. 조회된 단일 Roadmap 객체를 반환합니다.
        return roadmap;
    }

    public List<RoadmapWeek> getRoadmapWeeksForWeek(StudyMember studyMember, int week) {
        // 1. 우선 studyMember에게 해당하는 전체 Roadmap이 무엇인지 찾기
        Roadmap roadmap = getRoadmap(studyMember);

        // 2. 찾은 roadmap의 ID와 특정 주차(week)를 이용해 필요한 RoadmapWeek 목록만 DB에서 직접 조회
        return roadmapWeekRepository.findAllByRoadmapIdAndWeek(roadmap.getId(), week);
    }

    @Override
    public Roadmap getRoadmapById(Long roadmapId) {
        Roadmap roadmap = roadmapRepository.findById(roadmapId)
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_NOT_FOUND));

        return roadmap;
    }
}
