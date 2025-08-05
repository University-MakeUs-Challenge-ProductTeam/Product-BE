package umc.product.domain.roadmap.dto.response.admin;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Getter
@Builder
public class AdminRoadmapResponse {
  private Long id;
  private int week;
  private Part part;
  private List<String> titles;
}
