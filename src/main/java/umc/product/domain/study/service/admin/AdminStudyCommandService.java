package umc.product.domain.study.service.admin;

import umc.product.domain.study.dto.request.admin.AdminStudyRequest;
import umc.product.domain.study.entity.Study;

public interface AdminStudyCommandService {

    Study createStudy(AdminStudyRequest request);
}
