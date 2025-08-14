package umc.product.domain.roadmap.dto.response.admin;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import umc.product.domain.roadmap.entity.Roadmap;

@Getter
@Builder
@AllArgsConstructor
public class AdminRoadmapListResponse {
  private List<RoadmapInfo> roadmapList; // 실제 로드맵 목록
  private Integer listSize;      // 현재 페이지의 아이템 개수
  private Integer totalPage;     // 전체 페이지 수
  private Long totalElements;   // 전체 아이템 개수
  private boolean isFirst;       // 첫 페이지인지 여부
  private boolean isLast;        // 마지막 페이지인지 여부

  // Page<RoadmapInfo> 객체를 이 DTO로 변환하는 정적 메서드
  public static AdminRoadmapListResponse from(Page<RoadmapInfo> page) {
    return AdminRoadmapListResponse.builder()
        .roadmapList(page.getContent())
        .listSize(page.getNumberOfElements())
        .totalPage(page.getTotalPages())
        .totalElements(page.getTotalElements())
        .isFirst(page.isFirst())
        .isLast(page.isLast())
        .build();
  }

}
