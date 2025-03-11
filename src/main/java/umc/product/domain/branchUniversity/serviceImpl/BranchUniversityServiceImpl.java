package umc.product.domain.branchUniversity.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.branchUniversity.entity.BranchUniversity;
import umc.product.domain.branchUniversity.repository.BranchUniversityRepository;
import umc.product.domain.branchUniversity.service.BranchUniversityService;
import umc.product.domain.branchUniversity.status.BranchUniversityErrorStatus;
import umc.product.domain.university.entity.University;
import umc.product.global.common.exception.RestApiException;

@Service
@RequiredArgsConstructor
public class BranchUniversityServiceImpl implements BranchUniversityService {
    private final BranchUniversityRepository branchUniversityRepository;

    @Override
    public BranchUniversity getBranchUniversity(University university) {
        return branchUniversityRepository
                .findByUniversity(university)
                .orElseThrow(() -> new RestApiException(BranchUniversityErrorStatus.EMPTY_BRANCHUNIVERSITY));
    }
}
