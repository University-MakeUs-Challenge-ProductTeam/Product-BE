package umc.product.domain.checklist.service.admin;

import umc.product.domain.checklist.entity.ChecklistContent;
import umc.product.domain.semester.entity.Semester;

import java.util.List;

public interface AdminChecklistContentQueryService {

    List<ChecklistContent> getChecklistContentList(Semester semester, String part);
}
