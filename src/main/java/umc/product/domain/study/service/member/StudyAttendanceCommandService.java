package umc.product.domain.study.service.member;

import umc.product.domain.study.dto.request.member.StudyAttendanceRequest;
import umc.product.domain.study.dto.response.member.StudyCommonResponse;
import umc.product.domain.study.entity.StudyAttendance;

public interface StudyAttendanceCommandService {

    StudyCommonResponse updateAttendance(StudyAttendance studyAttendance, StudyAttendanceRequest request, Long studyId);
}
