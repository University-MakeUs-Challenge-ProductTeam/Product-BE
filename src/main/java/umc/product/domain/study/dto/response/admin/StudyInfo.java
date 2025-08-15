package umc.product.domain.study.dto.response.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;
import umc.product.domain.study.entity.enums.StudyType;

@Getter
public class StudyInfo {
  private Long studyId;
  private String universityName;
  private String studyName;
  private String semester;
  private String part;
  private Long memberCount; // 인원수
  private String type;
  private Integer currentWeek;

  @QueryProjection
  public StudyInfo(Long studyId, String universityName, String studyName, String semester, Part part, Long memberCount, StudyType studyType, Integer currentWeek) {
    this.studyId = studyId;
    this.universityName = universityName;
    this.studyName = studyName;
    this.semester = semester;
    this.part = part.name();
    this.memberCount = memberCount;
    this.type = studyType.getName();
    this.currentWeek = currentWeek;
  }

}
