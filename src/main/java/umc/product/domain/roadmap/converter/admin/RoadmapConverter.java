package umc.product.domain.roadmap.converter.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.product.domain.roadmap.dto.response.admin.AdminRoadmapResponse;
import umc.product.domain.roadmap.entity.Roadmap;
import umc.product.domain.roadmap.entity.RoadmapTitle;

@Component
@RequiredArgsConstructor
public class RoadmapConverter {

  public AdminRoadmapResponse toRoadmapDetailResponse(Roadmap roadmap) {
    return AdminRoadmapResponse.builder()
        .id(roadmap.getId())
        .week(roadmap.getWeek())
        .part(roadmap.getPart())
        .titles(
            roadmap.getRoadmapTitleList().stream()
                .map(RoadmapTitle::getTitle)
                .toList()
        )
        .build();
  }

}
