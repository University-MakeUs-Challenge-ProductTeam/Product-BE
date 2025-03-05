package umc.product.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.study.dto.response.StudyMemberResponse;
import umc.product.domain.study.dto.response.StudyResponse;
import umc.product.domain.study.dto.response.StudyWorkbookResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.mapper.StudyMapper;
import umc.product.domain.study.repository.StudyRepository;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyQueryServiceImpl implements StudyQueryService {

    private final StudyRepository studyRepository;
    private final StudyMapper studyMapper;

    @Override
    public Study getStudy(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_NOT_FOUND));
    }

    @Override
    public StudyResponse getStudyResponse(StudyMember studyMember, List<Roadmap> roadmapList) {
        // N + 1 문제를 해결하기 위해 querydsl을 사용하여 StudyMemberResponse 조회
        List<StudyMemberResponse> studyMemberResponseList = studyRepository.getStudyMembers(studyMember.getStudy());
        return studyMapper.toStudyResponse(studyMember, studyMemberResponseList, roadmapList);
    }

    @Override
    public StudyWorkbookResponse getStudyWorkbookResponse(StudyMember studyMember, int week, List<String> roadmapTitleList) {
        List<StudyMemberResponse> studyMemberResponseList = studyRepository.getStudyMembers(studyMember.getStudy());
        List<StudyWorkbookResponse.StudyChecklistResponse> studyChecklists = studyRepository.getStudyChecklists(studyMember.getId(), week);
        return studyMapper.toStudyWorkbookResponse(studyMember, studyMemberResponseList, week, roadmapTitleList, studyChecklists);
    }
}
