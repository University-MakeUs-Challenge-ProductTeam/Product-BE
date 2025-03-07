package umc.product.domain.study.service.member;

import umc.product.domain.study.dto.request.member.StudyModifyRequest;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;

public interface StudyCommandService {

    StudyCommonResponse modifyStudy(StudyMember studyMember, StudyModifyRequest request, Study study);
}
