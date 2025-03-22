package umc.product.domain.study.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.study.dto.response.member.*;
import umc.product.domain.study.dto.response.member.list.StudyListResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.converter.member.StudyConverter;
import umc.product.domain.study.repository.member.StudyRepository;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyQueryServiceImpl implements StudyQueryService {

    private final StudyRepository studyRepository;
    private final StudyConverter studyConverter;

    @Override
    public Study getStudy(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_NOT_FOUND));
    }

    @Override
    public StudyResponse getStudyResponse(StudyMember studyMember, List<Roadmap> roadmapList) {
        // N + 1 문제를 해결하기 위해 querydsl을 사용하여 StudyMemberResponse 조회
        List<StudyMemberResponse> studyMemberResponseList = studyRepository.getStudyMembers(studyMember.getStudy());
        // 로그인 사용자에 (나) 붙이기
        List<StudyMemberResponse> markedstudyMemberResponseList = mark(studyMemberResponseList, studyMember.getSemesterPart().getMember().getId());
        return studyConverter.toStudyResponse(studyMember, markedstudyMemberResponseList, roadmapList);
    }

    @Override
    public StudyWorkbookResponse getStudyWorkbookResponse(StudyMember studyMember, int week, List<String> roadmapTitleList, Long loginId) {
        // N + 1 문제를 해결하기 위해 querydsl을 사용하여 StudyMemberResponse 조회
        List<StudyMemberResponse> studyMemberResponseList = studyRepository.getStudyMembers(studyMember.getStudy());
        // 로그인 사용자에 (나) 붙이기
        List<StudyMemberResponse> markedstudyMemberResponseList = mark(studyMemberResponseList, loginId);
        List<StudyWorkbookResponse.StudyChecklistResponse> studyChecklistList = studyRepository.getStudyChecklists(studyMember.getId(), week);
        return studyConverter.toStudyWorkbookResponse(studyMember, markedstudyMemberResponseList, week, roadmapTitleList, studyChecklistList);
    }

    @Override
    public StudyWeekChecklistResponse getStudyChecklist(StudyMember studyMember, int week, List<String> roadmapTitleList) {
        List<StudyWeekChecklistResponse.ChecklistResponse> checklistResponseList = studyRepository.getChecklistResponses(studyMember, week);
        boolean postStatus = studyRepository.getPostStatus(studyMember, week);
        return studyConverter.toStudyWeekCheckListResponse(week, roadmapTitleList, postStatus, checklistResponseList);
    }

    // (나) 붙이는 메서드
    private List<StudyMemberResponse> mark(List<StudyMemberResponse> responseList, Long loginId) {
        return responseList.stream()
                .map(response -> response.getMemberId().equals(loginId)
                        ? response.toBuilder().nickName(response.getNickName() + "(나)").build()
                        : response)
                .collect(Collectors.toList());
    }

    @Override
    public StudyListResponse getStudyInfoList(Member member) {
        try {
            // N + 1 문제를 해결하기 위해 Querydsl 사용
            return studyConverter.toStudyListResponse(studyRepository.getStudyInfoList(member));
        } catch (Exception e) {
            throw new RestApiException(StudyErrorStatus.STUDY_INFO_GET_FAILED);
        }
    }
}
