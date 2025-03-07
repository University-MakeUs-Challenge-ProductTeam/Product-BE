package umc.product.domain.study.service;

import umc.product.domain.study.dto.common.request.StudyAttendanceRequest;
import umc.product.domain.study.dto.common.response.StudyCommonResponse;
import umc.product.domain.study.entity.StudyAttendance;

public interface StudyAttendanceCommandService {

    StudyCommonResponse updateAttendance(StudyAttendance studyAttendance, StudyAttendanceRequest request, Long studyId);
}
