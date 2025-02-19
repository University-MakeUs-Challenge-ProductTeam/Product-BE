package umc.product.domain.branch.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.branch.repository.BranchRepository;
import umc.product.domain.branch.service.BranchService;
import umc.product.domain.branchUniversity.repository.BranchUniversityRepository;
import umc.product.domain.branchUniversity.service.BranchUniversityService;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
}
