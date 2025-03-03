package umc.product.domain.member.serviceImpl.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import umc.product.domain.member.service.member.MemberCodeService;
import umc.product.global.common.exception.RestApiException;

import static umc.product.domain.member.status.MemberErrorStatus.NOT_VALID_CODE;

@Service
@RequiredArgsConstructor
public class MemberCodeServiceImpl implements MemberCodeService {
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public Long verifyAppCode(String code) {
        String key = "code:" + code;
        String value = stringRedisTemplate.opsForValue().get(key);
        if(value == null) throw new RestApiException(NOT_VALID_CODE);
        return Long.parseLong(value);
    }
}
