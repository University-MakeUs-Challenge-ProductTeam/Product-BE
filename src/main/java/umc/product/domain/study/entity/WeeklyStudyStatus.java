package umc.product.domain.study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.product.domain.study.entity.enums.PassStatus;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeeklyStudyStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 어떤 스터디 멤버에 대한 상태인지 (N:1)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_member_id")
  private StudyMember studyMember;

  @Column(nullable = false)
  private int week;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PassStatus status;

  public void updateStatus(PassStatus status) {
    this.status = status;
  }
}
