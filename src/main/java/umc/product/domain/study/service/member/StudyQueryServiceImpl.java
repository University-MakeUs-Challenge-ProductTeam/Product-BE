package umc.product.domain.study.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapSemester;
import umc.product.domain.roadmap.repository.RoadmapRepository;
import umc.product.domain.roadmap.repository.RoadmapSemesterRepository;
import umc.product.domain.roadmap.status.RoadmapErrorStatus;
import umc.product.domain.semester.entity.Semester;
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
    private final RoadmapRepository roadmapRepository;
    private final RoadmapSemesterRepository roadmapSemesterRepository;

    @Override
    public Study getStudy(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_NOT_FOUND));
    }

    @Override
    public StudyResponse getStudyResponse(StudyMember studyMember, Roadmap roadmap) {

        // N + 1 문제를 해결하기 위해 querydsl을 사용하여 StudyMemberResponse 조회
        List<StudyMemberResponse> studyMemberResponseList = studyRepository.getStudyMembers(studyMember.getStudy());
        // 로그인 사용자에 (나) 붙이기
        List<StudyMemberResponse> markedstudyMemberResponseList = mark(studyMemberResponseList, studyMember.getSemesterPart().getMember().getId());

        return studyConverter.toStudyResponse(studyMember, markedstudyMemberResponseList, roadmap);
    }

    @Override
    public StudyWorkbookResponse getStudyWorkbookResponse(StudyMember studyMember, int week, List<String> workbookContents, Long loginId) {

        Semester semester = studyMember.getSemesterPart().getSemester();
        Part part = studyMember.getSemesterPart().getPart();

        Roadmap roadmap = roadmapRepository.findBySemesterIdAndPart(semester.getId(), part)
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_NOT_FOUND));

        RoadmapSemester roadmapSemester = roadmapSemesterRepository.findByRoadmapAndSemester_Id(roadmap, semester.getId())
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_SEMESTER_NOT_FOUND));

        // N + 1 문제를 해결하기 위해 querydsl을 사용하여 StudyMemberResponse 조회
        List<StudyMemberResponse> studyMemberResponseList = studyRepository.getStudyMembers(studyMember.getStudy());
        // 로그인 사용자에 (나) 붙이기
        List<StudyMemberResponse> markedstudyMemberResponseList = mark(studyMemberResponseList, loginId);
        List<StudyWorkbookResponse.StudyChecklistResponse> studyChecklistList = studyRepository.getStudyChecklists(studyMember.getId(), roadmapSemester, week);
        return studyConverter.toStudyWorkbookResponse(studyMember, markedstudyMemberResponseList, week, workbookContents, studyChecklistList);
    }

    @Override
    public StudyWeekChecklistResponse getStudyChecklist(StudyMember studyMember, int week, List<String> workbookContents) {
        // studyMember에서 Semester와 Part 정보를 조회
        Semester semester = studyMember.getSemesterPart().getSemester();
        Part part = studyMember.getSemesterPart().getPart();

        // 위 정보를 이용해 이 스터디에 해당하는 Roadmap을 조회
        Roadmap roadmap = roadmapRepository.findBySemesterIdAndPart(semester.getId(), part)
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_NOT_FOUND));

        // 찾은 Roadmap과 Semester로 최종적으로 필요한 RoadmapSemester 조회
        RoadmapSemester roadmapSemester = roadmapSemesterRepository.findByRoadmapAndSemester_Id(roadmap, semester.getId())
            .orElseThrow(() -> new RestApiException(RoadmapErrorStatus.ROADMAP_SEMESTER_NOT_FOUND));

        List<StudyWeekChecklistResponse.ChecklistResponse> checklistResponseList =
            studyRepository.getChecklistResponses(studyMember.getId(), roadmapSemester, week);

        boolean postStatus = studyRepository.getPostStatus(studyMember, week);
        return studyConverter.toStudyWeekCheckListResponse(week, workbookContents, postStatus, checklistResponseList);
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
