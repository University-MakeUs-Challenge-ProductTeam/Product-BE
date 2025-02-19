package umc.product.domain.branch.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.product.domain.branch.service.BranchService;
import umc.product.domain.branchUniversity.service.BranchUniversityService;

@Tag(name = "지부 API", description = "지부 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/branches")
public class BranchController {
    private final BranchService branchService;
}
