package umc.product.domain.branchUniversity.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.branchUniversity.repository.BranchUniversityRepository;
import umc.product.domain.branchUniversity.service.BranchUniversityService;

@Service
@RequiredArgsConstructor
public class BranchUniversityServiceImpl implements BranchUniversityService {
    private final BranchUniversityRepository branchUniversityRepository;
}
