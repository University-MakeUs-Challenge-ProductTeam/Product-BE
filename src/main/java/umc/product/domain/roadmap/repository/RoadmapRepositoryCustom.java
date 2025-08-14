package umc.product.domain.roadmap.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.roadmap.dto.response.admin.RoadmapInfo;

public interface RoadmapRepositoryCustom {
  Page<RoadmapInfo> searchRoadmaps(Long semesterId, Part part, String keyword, Pageable pageable);

}
