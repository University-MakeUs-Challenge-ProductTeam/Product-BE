package umc.product.domain.study.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.study.entity.StudyAttendance;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.repository.StudyAttendanceRepository;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyAttendanceQueryServiceImpl implements StudyAttendanceQueryService {

    private final StudyAttendanceRepository studyAttendanceRepository;

    @Override
    public StudyAttendance getStudyAttendance(StudyMember studyMember, int week) {
        return studyAttendanceRepository.findByStudyMemberAndWeek(studyMember, week)
                .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_ATTENDANCE_NOT_FOUND));
    }
}
