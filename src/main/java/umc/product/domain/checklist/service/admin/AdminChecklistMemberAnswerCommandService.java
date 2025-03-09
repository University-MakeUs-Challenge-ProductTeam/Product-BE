package umc.product.domain.checklist.service.admin;

import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface AdminChecklistMemberAnswerCommandService {

    void createChecklistMemberAnswer(List<ChecklistContent> checklistContentList, List<StudyMember> studyMemberList);
}
