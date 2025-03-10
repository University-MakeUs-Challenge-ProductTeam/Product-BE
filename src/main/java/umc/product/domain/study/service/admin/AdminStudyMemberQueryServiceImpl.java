package umc.product.domain.study.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.repository.member.StudyMemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStudyMemberQueryServiceImpl implements AdminStudyMemberQueryService {

    private final StudyMemberRepository studyMemberRepository;

    @Override
    public List<Member> getMemberList(Study study) {
        return studyMemberRepository.findMembersByStudy(study);
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
}
