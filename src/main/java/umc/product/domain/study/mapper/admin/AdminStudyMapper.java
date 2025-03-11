package umc.product.domain.study.mapper.admin;

import org.springframework.stereotype.Component;
import umc.product.domain.study.dto.request.admin.AdminStudyRequest;
import umc.product.domain.study.entity.Study;
import umc.product.domain.study.entity.enums.StudyType;

@Component
public class AdminStudyMapper {

    public Study toStudy(AdminStudyRequest request) {
        return Study.builder()
                .name(request.getStudyName())
                .studyType(StudyType.valueOf(request.getStudyType().toUpperCase()))
                .currentWeek(request.getCurrentWeek())
                .build();
    }
}
