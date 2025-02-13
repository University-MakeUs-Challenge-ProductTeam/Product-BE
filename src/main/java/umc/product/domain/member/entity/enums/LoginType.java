package umc.product.domain.member.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType {
    KAKAO("카카오"),
    GOOGLE("구글"),
    NAVER("네이버"),
    APPLE("애플"),
    ANONYMOUS("비회원"),
    INTERNAL("자체 로그인")
    ;

    private final String toKorean;
}
