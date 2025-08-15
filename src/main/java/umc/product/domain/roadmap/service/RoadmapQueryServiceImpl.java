package umc.product.domain.roadmap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.entity.RoadmapWeek;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.repository.RoadmapSemesterRepository;
import umc.product.domain.roadmap.repository.RoadmapWeekRepository;
import umc.product.domain.roadmap.status.RoadmapErrorStatus;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.study.entity.Study;
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
    private final RoadmapSemesterRepository roadmapSemesterRepository;

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

    @Override
    public RoadmapSemester getRoadmapSemester(Long semesterId, Part part) {
        // semesterId와 part로 Roadmap 조회
        Roadmap roadmap = roadmapRepository.findBySemesterIdAndPart(semesterId, part)
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_NOT_FOUND));

        // 찾은 Roadmap과 semesterId로 RoadmapSemester 조회
        return roadmapSemesterRepository.findByRoadmapAndSemester_Id(roadmap, semesterId)
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_SEMESTER_NOT_FOUND));
    }

    @Override
    public Roadmap getRoadmapBySemesterAndPart(Long semesterId, Part part) {
        return roadmapRepository.findBySemesterIdAndPart(semesterId, part)
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_NOT_FOUND));
    }

    @Override
    public Roadmap getRoadmapByStudy(Study study) {
        // 스터디에 멤버가 한 명도 없는 예외 경우를 처리
        if (study.getStudyMemberList().isEmpty()) {
            throw new RestApiException(StudyErrorStatus.STUDY_HAS_NO_MEMBERS); // 예시 예외
        }

        // 어떤 멤버든 동일한 SemesterPart에 속하므로 첫 번째 멤버를 기준
        SemesterPart semesterPart = study.getStudyMemberList().get(0).getSemesterPart();
        Long semesterId = semesterPart.getSemester().getId();
        Part part = semesterPart.getPart();

        return roadmapRepository.findBySemesterIdAndPart(semesterId, part)
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_NOT_FOUND));
    }

    @Override
    public RoadmapSemester getRoadmapSemesterByRoadmapAndStudy(Roadmap roadmap, Study study) {
        if (study.getStudyMemberList().isEmpty()) {
            throw new RestApiException(StudyErrorStatus.STUDY_HAS_NO_MEMBERS);
        }

        // 스터디의 기수(Semester) 정보를 가져옵니다.
        Semester semester = study.getStudyMemberList().get(0).getSemesterPart().getSemester();

        // Roadmap과 Semester로 RoadmapSemester를 조회합니다.
        return roadmapSemesterRepository.findByRoadmapAndSemester_Id(roadmap, semester.getId())
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_SEMESTER_NOT_FOUND));
    }
}
