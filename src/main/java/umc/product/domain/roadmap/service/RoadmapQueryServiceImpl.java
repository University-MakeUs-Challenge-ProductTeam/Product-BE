package umc.product.domain.roadmap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.repository.RoadmapRepository;
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

    @Override
    public List<Roadmap> getRoadmapList(StudyMember studyMember) {

        SemesterPart semesterPart = studyMember.getSemesterPart();
        Long semesterId = semesterPart.getSemester().getId();
        Part part = semesterPart.getPart();

        List<Roadmap> roadmapList = roadmapRepository.findAllBySemesterIdAndPart(semesterId, part);
        if (roadmapList.isEmpty()) {
            throw new RestApiException(StudyErrorStatus.STUDY_ROADMAP_NOT_FOUND);
        }
        return roadmapList;
    }
}
