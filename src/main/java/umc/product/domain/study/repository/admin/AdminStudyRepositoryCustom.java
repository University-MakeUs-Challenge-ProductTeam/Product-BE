package umc.product.domain.study.repository.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.study.dto.response.admin.StudyInfo;

public interface AdminStudyRepositoryCustom {
  Page<StudyInfo> searchStudies(Long semesterId, Part part, String keyword, Long universityId, Pageable pageable);
}
