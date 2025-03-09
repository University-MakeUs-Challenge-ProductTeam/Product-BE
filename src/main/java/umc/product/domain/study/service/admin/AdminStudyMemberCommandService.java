package umc.product.domain.study.service.admin;

import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface AdminStudyMemberCommandService {

    List<StudyMember> createStudyMember(Study study, List<AdminStudyMemberRequest> memberRequestList, List<SemesterPart> semesterPartList);
}
