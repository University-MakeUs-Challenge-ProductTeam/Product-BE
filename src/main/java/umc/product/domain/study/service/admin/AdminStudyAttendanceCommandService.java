package umc.product.domain.study.service.admin;

import umc.product.domain.study.entity.StudyMember;

import java.util.List;

public interface AdminStudyAttendanceCommandService {
    void createStudyAttendance(List<StudyMember> studyMemberList);
}
