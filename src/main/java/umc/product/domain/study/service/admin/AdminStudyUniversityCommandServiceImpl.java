package umc.product.domain.study.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.branch.entity.Branch;
import umc.product.domain.branchUniversity.entity.BranchUniversity;
import umc.product.domain.branchUniversity.service.BranchUniversityService;
import umc.product.domain.member.entity.Member;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.StudyUniversity;
import umc.product.domain.study.entity.enums.StudyType;
import umc.product.domain.study.mapper.admin.AdminStudyUniversityMapper;
import umc.product.domain.study.repository.admin.AdminStudyUniversityRepository;
import umc.product.domain.study.status.StudyErrorStatus;
import umc.product.domain.university.entity.University;
import umc.product.global.common.exception.RestApiException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStudyUniversityCommandServiceImpl implements AdminStudyUniversityCommandService {

    private final AdminStudyUniversityMapper adminStudyUniversityMapper;
    private final AdminStudyUniversityRepository adminStudyUniversityRepository;
    private final BranchUniversityService branchUniversityService;

    @Override
    public void createStudyUniversity(Study study, List<Member> memberList) {
        List<University> universityList = adminStudyUniversityMapper.toUniversity(memberList);

        if (study.getStudyType() == StudyType.ADMIN) {
            return;
        }

        if (universityList.isEmpty()) {
            throw new RestApiException(StudyErrorStatus.UNIVERSITY_LIST_EMPTY);
        }

        // SCHOOL이면 모든 학교가 같아야 되고, BRANCH면 같은 지부 내에 속한 학교여야 함
        if (study.getStudyType() == StudyType.SCHOOL) {
            // SCHOOL -> 대학 목록 1개
            if (universityList.size() != 1) {
                throw new RestApiException(StudyErrorStatus.INVALID_STUDY_TYPE_SCHOOL);
            }
        } else if (study.getStudyType() == StudyType.BRANCH) {
            // BRANCH -> 모든 대학이 같은 지부에 속해 있는지 검증
            if (universityList.size() < 2) {
                throw new RestApiException(StudyErrorStatus.INVALID_STUDY_TYPE_BRANCH);
            }
            // 첫 번째 대학의 Branch 정보를 기준으로 검증
            BranchUniversity branchUniversity = branchUniversityService.getBranchUniversity(universityList.get(0));
            Branch branch = branchUniversity.getBranch();
            boolean sameBranchStatus = universityList.stream()
                    .allMatch(u -> branchUniversityService.getBranchUniversity(u).getBranch().getId().equals(branch.getId()));
            if (!sameBranchStatus) {
                throw new RestApiException(StudyErrorStatus.UNIVERSITY_DIFFERENT_BRANCH);
            }
        } else {
            throw new RestApiException(StudyErrorStatus.UNSUPPORTED_STUDY_TYPE);
        }

        // StudyUniversity 엔티티 생성
        List<StudyUniversity> studyUniversityList = adminStudyUniversityMapper.toStudyUniversityList(study, universityList);
        adminStudyUniversityRepository.saveAll(studyUniversityList);
    }
}
