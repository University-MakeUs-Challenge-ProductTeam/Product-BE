package umc.product.domain.study.dto.response.admin;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
public class AdminStudyListResponse {

  private List<StudyInfo> studyList;
  private Integer listSize;
  private Integer totalPage;
  private Long totalElements;
  private boolean isFirst;
  private boolean isLast;

  public static AdminStudyListResponse from(Page<StudyInfo> page) {
    return AdminStudyListResponse.builder()
        .studyList(page.getContent())
        .listSize(page.getNumberOfElements())
        .totalPage(page.getTotalPages())
        .totalElements(page.getTotalElements())
        .isFirst(page.isFirst())
        .isLast(page.isLast())
        .build();
  }

}
