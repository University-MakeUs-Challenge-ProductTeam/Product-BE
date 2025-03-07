package umc.product.domain.study.service.member;

import umc.product.domain.study.entity.StudyAttendance;
import umc.product.domain.study.entity.StudyMember;

public interface StudyAttendanceQueryService {

    StudyAttendance getStudyAttendance(StudyMember studyMember, int week);
}
