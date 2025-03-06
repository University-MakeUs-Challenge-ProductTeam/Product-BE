package umc.product.domain.study.service;

import umc.product.domain.study.dto.request.StudyModifyRequest;
import umc.product.domain.study.dto.response.StudyCommonResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.entity.enums.StudyRole;

public interface StudyCommandService {

    StudyCommonResponse modifyStudy(StudyMember studyMember, StudyModifyRequest request, Study study);
}
