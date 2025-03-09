package umc.product.domain.branchUniversity.service;

import umc.product.domain.branchUniversity.entity.BranchUniversity;
import umc.product.domain.university.entity.University;

public interface BranchUniversityService {

    BranchUniversity getBranchUniversity(University university);
}
