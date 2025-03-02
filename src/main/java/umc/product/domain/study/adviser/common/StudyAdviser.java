package umc.product.domain.study.adviser.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.dto.request.StudyAttendanceRequest;
import umc.product.domain.study.dto.request.StudyModifyRequest;
import umc.product.domain.study.dto.response.StudyCommonResponse;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyAttendance;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.service.*;

@Component
@RequiredArgsConstructor
public class StudyAdviser {

    private final StudyMemberQueryService studyMemberQueryService;
    private final StudyQueryService studyQueryService;
    private final StudyCommandService studyCommandService;
    private final StudyAttendanceQueryService studyAttendanceQueryService;
    private final StudyAttendanceCommandService studyAttendanceCommandService;

    // 스터디 정보 수정
    public StudyCommonResponse modifyStudy(Member member, StudyModifyRequest request, Long studyId) {
        StudyMember studyMember = studyMemberQueryService.getStudyMember(member, studyId);
        Study study = studyQueryService.getStudy(studyId);
        return studyCommandService.modifyStudy(studyMember, request, study);
    }

    // 스터디 참석 여부 체크
    public StudyCommonResponse checkAttendance(Member member, StudyAttendanceRequest request, Long studyId, int week) {
        // member와 studyId로 StudyMember 객체를 찾고, studyMember와 week로 특정 주차의 StudyAttendance 객체 찾기
        StudyMember studyMember = studyMemberQueryService.getStudyMember(member, studyId);
        StudyAttendance studyAttendance = studyAttendanceQueryService.getStudyAttendance(studyMember, week);

        // request에서 참석 여부를 받아 check_status 필드 변경
        return studyAttendanceCommandService.updateAttendance(studyAttendance, request, studyId);
    }
}
