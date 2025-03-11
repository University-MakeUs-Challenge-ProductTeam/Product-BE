package umc.product.domain.checklist.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.checklist.repository.ChecklistContentRepository;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminChecklistContentQueryServiceImpl implements AdminChecklistContentQueryService {

    private final ChecklistContentRepository checklistContentRepository;

    @Override
    public List<ChecklistContent> getChecklistContentList(Semester semester, String part) {

        try {
            List<ChecklistContent> checklistContentList = checklistContentRepository.findChecklistContentsBySemesterAndPart(semester, Part.valueOf(part.toUpperCase()));
            if (checklistContentList.isEmpty()) {
                throw new RestApiException(StudyErrorStatus.STUDY_CHECKLIST_NOT_FOUND);
            }
            return checklistContentList;
        } catch (IllegalArgumentException e) {
            throw new RestApiException(StudyErrorStatus.STUDY_ROADMAP_NOT_FOUND);
        }
    }

}
