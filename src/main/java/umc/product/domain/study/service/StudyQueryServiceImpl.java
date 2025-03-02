package umc.product.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.repository.StudyRepository;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyQueryServiceImpl implements StudyQueryService {

    private final StudyRepository studyRepository;

    @Override
    public Study getStudy(Long studyId) {
        return studyRepository.findById(studyId)
                .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_NOT_FOUND));
    }
}
