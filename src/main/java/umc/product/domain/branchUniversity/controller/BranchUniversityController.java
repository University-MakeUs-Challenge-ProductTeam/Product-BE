package umc.product.domain.branchUniversity.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import umc.product.domain.branchUniversity.service.BranchUniversityService;
import umc.product.domain.notice.service.NoticeService;

@Tag(name = "지부대학 API", description = "지부대학 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/branchUniversities")
public class BranchUniversityController {
    private final BranchUniversityService branchUniversityService;
}
