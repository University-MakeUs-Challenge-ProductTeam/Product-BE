package umc.product.domain.checklist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.checklist.entity.ChecklistMemberAnswer;
import umc.product.domain.checklist.repository.ChecklistMemberAnswerRepository;
import umc.product.domain.study.dto.request.StudyChecklistListRequest;
import umc.product.domain.study.dto.response.StudyCommonResponse;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ChecklistCommandServiceImpl implements ChecklistCommandService {

    private final ChecklistMemberAnswerRepository checklistMemberAnswerRepository;

    @Override
    public StudyCommonResponse updateChecklistAnswers(StudyMember studyMember, int week, List<StudyChecklistListRequest.StudyChecklistRequest> answers) {
        // StudyMember의 week에 해당하는 ChecklistMemberAnswer 리스트 조회
        List<ChecklistMemberAnswer> checklistAnswerList = checklistMemberAnswerRepository.findAllByStudyMemberAndWeek(studyMember, week);

        // contentId에 해당하는 ChecklistMemberAnswer 찾기
        for (StudyChecklistListRequest.StudyChecklistRequest answer : answers) {
            ChecklistMemberAnswer checklistMemberAnswer = checklistAnswerList.stream()
                    .filter(cma -> cma.getChecklistContent().getId().equals(answer.getContentId()))
                    .findFirst()
                    .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_CHECKLIST_NOT_FOUND));

            // checkStatus 업데이트(개수가 많지 않아 Bulk Update 필요 없다고 판단
            checklistMemberAnswer.updateCheckStatus(answer.isCheckStatus());
        }

        return StudyCommonResponse.from(studyMember.getStudy().getId());
    }
}
