package umc.product.domain.study.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.Member;
import umc.product.domain.semester.entity.Semester;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.repository.admin.AdminStudyMemberRepository;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.global.common.exception.RestApiException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStudyMemberQueryServiceImpl implements AdminStudyMemberQueryService {

    private final AdminStudyMemberRepository adminStudyMemberRepository;

    @Override
    public List<Member> getMemberList(Study study) {
        return adminStudyMemberRepository.findMembersByStudy(study);
    }

    @Override
    public List<StudyMember> getRemovedStudyMemberList(Study study, List<AdminStudyMemberRequest> memberRequestList) {
        // 새로운 스터디원들의 memberIdList
        List<Long> newMemberIdList = memberRequestList.stream()
                .map(AdminStudyMemberRequest::getMemberId)
                .collect(Collectors.toList());

        // 기존 스터디 멤버 목록
        List<StudyMember> originalStudyMemberList = study.getStudyMemberList();

        // 삭제해야 할 StudyMember 반환
        return originalStudyMemberList.stream()
                .filter(sm -> !newMemberIdList.contains(sm.getSemesterPart().getMember().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void validateStudyMember(List<SemesterPart> semesterPartList) {
        Semester semester = semesterPartList.get(0).getSemester();

        // 각 SemesterPart에서 멤버 id 추출
        List<Long> memberIdList = semesterPartList.stream()
                .map(sp -> sp.getMember().getId())
                .distinct() // 혹시 몰라 distinct 추가
                .collect(Collectors.toList());

        // 해당 기수에 이미 스터디에 참여하고 있는 멤버가 있는지 검증
        boolean exists = adminStudyMemberRepository.existsBySemesterPart_Member_IdInAndSemesterPart_Semester(memberIdList, semester);

        if (exists) {
            throw new RestApiException(StudyErrorStatus.STUDY_ALREADY_EXISTS);
        }
    }
}
