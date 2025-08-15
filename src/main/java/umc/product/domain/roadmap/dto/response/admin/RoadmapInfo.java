package umc.product.domain.roadmap.dto.response.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Getter
public class RoadmapInfo {
  private Long roadmapId;
  private String title;
  private String semester;
  private String part;
  private Integer totalWeeks; // 총 몇 주차인지

  @QueryProjection
  public RoadmapInfo(Long roadmapId, String title, String semester, Part part, Integer totalWeeks) {
    this.roadmapId = roadmapId;
    this.title = title;
    this.semester = semester;
    this.part = part.name();
    this.totalWeeks = totalWeeks;
  }

}
