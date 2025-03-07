package umc.product.domain.study.service;

import umc.product.domain.study.dto.common.request.StudyModifyRequest;
import umc.product.domain.study.dto.common.response.StudyCommonResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

public interface StudyCommandService {

    StudyCommonResponse modifyStudy(StudyMember studyMember, StudyModifyRequest request, Study study);
}
