package umc.product.domain.study.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import umc.product.domain.study.dto.request.admin.AdminStudyRequest;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.mapper.admin.AdminStudyMapper;
import umc.product.domain.study.repository.admin.AdminStudyRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStudyCommandServiceImpl implements AdminStudyCommandService {

    private final AdminStudyMapper adminStudyMapper;
    private final AdminStudyRepository adminStudyRepository;

    @Override
    public Study createStudy(AdminStudyRequest request) {
        Study study = adminStudyMapper.toStudy(request);
        adminStudyRepository.save(study);
        return study;
    }
}
