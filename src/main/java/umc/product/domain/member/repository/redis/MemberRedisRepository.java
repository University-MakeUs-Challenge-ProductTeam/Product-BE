package umc.product.domain.member.repository.redis;

import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.RefreshToken;

import java.util.List;
import java.util.Map;

public interface MemberRedisRepository {
    RefreshToken saveRefreshToken(String refreshToken, Long memberId);
    void deleteRefreshToken(Long memberId);
    boolean existRefreshToken(String refreshToken, Long memberId);
    List<String> getAppCodeList(List<Member> memberList);
    void saveAdminCode(String universityName,
                       String code);

    String verifyWebAdminCode(String code);
    Long verifyAppCode(String code);

    void saveAppCodeList(Map<String, Member> codeMap);
}
