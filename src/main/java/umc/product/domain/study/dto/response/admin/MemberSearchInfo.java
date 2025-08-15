package umc.product.domain.study.dto.response.admin;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import umc.product.domain.member.entity.enums.Part;

@Getter
public class MemberSearchInfo {

  private Long memberId;
  private String nickname;
  private String name;
  private String universityName;
  private String part; // 마지막 활성 파트
  private String lastActiveSemester; // 마지막 활성 기수

  @QueryProjection
  public MemberSearchInfo(Long memberId, String nickname, String name, String universityName, Part part, String semesterName) {
    this.memberId = memberId;
    this.nickname = nickname;
    this.name = name;
    this.universityName = universityName;
    this.part = part.name();
    this.lastActiveSemester = semesterName;
  }

}
