package umc.product.domain.study.adviser.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.dto.request.StudyModifyRequest;
import umc.product.domain.study.dto.response.StudyCommonResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.service.StudyCommandService;
import umc.product.domain.study.service.StudyMemberQueryService;
import umc.product.domain.study.service.StudyQueryService;

@Component
@RequiredArgsConstructor
public class StudyAdviser {

    private final StudyMemberQueryService studyMemberQueryService;
    private final StudyQueryService studyQueryService;
    private final StudyCommandService studyCommandService;

    public StudyCommonResponse modifyStudy(Member member, StudyModifyRequest request, Long studyId) {
        StudyMember studyMember = studyMemberQueryService.getStudyRole(member, studyId);
        Study study = studyQueryService.getStudy(studyId);
        return studyCommandService.modifyStudy(studyMember, request, study);
    }
}
