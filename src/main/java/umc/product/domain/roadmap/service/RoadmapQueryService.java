package umc.product.domain.roadmap.service;

import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface RoadmapQueryService {

    Roadmap getRoadmap(StudyMember studyMember);

    List<Roadmap> getRoadmapList(StudyMember studyMember);

    List<String> getRoadmapTitleList(StudyMember studyMember, int week);

}
