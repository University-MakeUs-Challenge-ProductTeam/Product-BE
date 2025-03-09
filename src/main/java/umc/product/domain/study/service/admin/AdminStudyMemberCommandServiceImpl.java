package umc.product.domain.study.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.semester.entity.SemesterPart;
import umc.product.domain.study.dto.request.admin.AdminStudyMemberRequest;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyMember;
import umc.product.domain.study.mapper.admin.AdminStudyMemberMapper;
import umc.product.domain.study.repository.admin.AdminStudyMemberRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStudyMemberCommandServiceImpl implements AdminStudyMemberCommandService {

    private final AdminStudyMemberMapper adminStudyMemberMapper;
    private final AdminStudyMemberRepository adminStudyMemberRepository;

    @Override
    public List<StudyMember> createStudyMember(Study study, List<AdminStudyMemberRequest> memberRequestList, List<SemesterPart> semesterPartList) {
        List<StudyMember> studyMemberList = adminStudyMemberMapper.getStudyMemberList(study, memberRequestList, semesterPartList);
        adminStudyMemberRepository.saveAll(studyMemberList);
        return studyMemberList;
    }
}
