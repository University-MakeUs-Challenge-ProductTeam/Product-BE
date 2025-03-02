package umc.product.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.study.dto.request.StudyModifyRequest;
import umc.product.domain.study.dto.response.StudyCommonResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.entity.enums.StudyRole;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.common.exception.code.status.GlobalErrorStatus;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudyCommandServiceImpl implements StudyCommandService {

    @Override
    public StudyCommonResponse modifyStudy(StudyMember studyMember, StudyModifyRequest request, Study study) {
        // 사용자가 스터디 리더인지 검증
        if (studyMember.getStudyRole() != StudyRole.LEADER) {
            throw new RestApiException(GlobalErrorStatus._FORBIDDEN);
        }
        // 스터디 정보 수정        todo - 스터디 주차는 어떻게 수정할지 미정
        study.changeName(request.getStudyName());

        return StudyCommonResponse.from(study.getId());
    }
}
