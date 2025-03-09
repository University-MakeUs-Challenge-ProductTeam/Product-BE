package umc.product.domain.checklist.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.checklist.entity.ChecklistMemberAnswer;
import umc.product.domain.checklist.repository.ChecklistMemberAnswerRepository;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminChecklistMemberAnswerCommandServiceImpl implements AdminChecklistMemberAnswerCommandService {

    private final ChecklistMemberAnswerRepository checklistMemberAnswerRepository;

    @Override
    public void createChecklistMemberAnswer(List<ChecklistContent> checklistContentList, List<StudyMember> studyMemberList) {

        List<ChecklistMemberAnswer> answerList = studyMemberList.stream()
                .flatMap(studyMember -> checklistContentList.stream()
                        .map(content -> ChecklistMemberAnswer.builder()
                                .checkStatus(false) // 초기 상태 false
                                .checklistContent(content)
                                .studyMember(studyMember)
                                .build()))
                .collect(Collectors.toList());
        checklistMemberAnswerRepository.saveAll(answerList);
    }
}
