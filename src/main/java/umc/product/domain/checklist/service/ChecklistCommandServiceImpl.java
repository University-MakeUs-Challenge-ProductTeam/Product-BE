package umc.product.domain.checklist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.checklist.entity.ChecklistMemberAnswer;
import umc.product.domain.checklist.entity.enums.ChecklistType;
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
            ChecklistMemberAnswer targetAnswer = checklistAnswerList.stream()
                    .filter(cma -> cma.getChecklistContent().getId().equals(answer.getContentId()))
                    .findFirst()
                    .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_CHECKLIST_NOT_FOUND));

            // SELECT 타입 -> 하나만 체크되도록 검증
            if (targetAnswer.getChecklistContent().getChecklist().getChecklistType() == ChecklistType.SELECT) {
                updateSelectTypeChecklist(checklistAnswerList, targetAnswer, answer.isCheckStatus());
            } else {
                // MULTIPLE 타입 -> 바로 업데이트
                targetAnswer.updateCheckStatus(answer.isCheckStatus());
            }
        }

        return StudyCommonResponse.from(studyMember.getStudy().getId());
    }

    private void updateSelectTypeChecklist(List<ChecklistMemberAnswer> checklistAnswerList, ChecklistMemberAnswer targetAnswer, boolean newStatus) {
        Long checklistId = targetAnswer.getChecklistContent().getChecklist().getId();
        if (newStatus) {
            // 다른 content의 checkStatus를 false로 초기화
            checklistAnswerList.stream()
                    .filter(cma -> cma.getChecklistContent().getChecklist().getId().equals(checklistId))
                    .forEach(cma -> cma.updateCheckStatus(false));
            // 대상 checkStatus만 true로 업데이트
            targetAnswer.updateCheckStatus(true);
        } else {
            // 요청이 false이면 해당 checkStatus만 업데이트
            targetAnswer.updateCheckStatus(false);
        }
    }
}
