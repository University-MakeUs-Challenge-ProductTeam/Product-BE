package umc.product.domain.study.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PassStatus {
  PENDING, // 평가 대기
  PASS,    // 통과
  OUT;     // 아웃

}
