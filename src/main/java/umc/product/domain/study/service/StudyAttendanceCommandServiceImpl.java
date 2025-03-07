package umc.product.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.study.dto.common.request.StudyAttendanceRequest;
import umc.product.domain.study.dto.common.response.StudyCommonResponse;
import umc.product.domain.study.entity.StudyAttendance;
import umc.product.domain.study.entity.enums.Check;
import umc.product.domain.study.repository.StudyAttendanceRepository;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StudyAttendanceCommandServiceImpl implements StudyAttendanceCommandService {

    private final StudyAttendanceRepository studyAttendanceRepository;

    @Override
    public StudyCommonResponse updateAttendance(StudyAttendance studyAttendance, StudyAttendanceRequest request, Long studyId) {

        try {
            studyAttendance.updateCheckStatus(Check.valueOf(request.getAttendance().toUpperCase()));
            studyAttendanceRepository.save(studyAttendance);
            return StudyCommonResponse.from(studyId);
        } catch (IllegalArgumentException e) {
            throw new RestApiException(StudyErrorStatus.INVALID_ATTENDANCE_VALUE);
        }
    }
}