package umc.product.domain.study.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.repository.member.StudyMemberRepository;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyMemberQueryServiceImpl implements StudyMemberQueryService {

    private final StudyMemberRepository studyMemberRepository;

    @Override
    public StudyMember getStudyMember(Member member, Long studyId) {
        return studyMemberRepository.findBySemesterPart_MemberAndStudy_Id(member, studyId)
                .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_MEMBER_NOT_FOUND));
    }

    @Override
    public StudyMember getStudyMemberFetch(Member member, Long studyId) {
        return studyMemberRepository.findBySemesterPart_MemberAndStudy_IdFetch(member, studyId)
                .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_MEMBER_NOT_FOUND));
    }

    @Override
    public StudyMember getStudyMember(Semester currentSemester, Member member) {
        return studyMemberRepository.findStudyMember(currentSemester, member)
                .orElseThrow(() -> new RestApiException(StudyErrorStatus.STUDY_MEMBER_SEMESTER_NOT_FOUND));
    }
}

