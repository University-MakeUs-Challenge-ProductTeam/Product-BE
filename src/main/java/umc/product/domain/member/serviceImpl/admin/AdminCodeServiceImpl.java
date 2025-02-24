package umc.product.domain.member.serviceImpl.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import umc.product.domain.member.dto.request.admin.AdminCodeRequest;
import umc.product.domain.member.service.admin.AdminCodeService;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminCodeServiceImpl implements AdminCodeService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final long EXPIRATION_TIME = 60 * 30;
    private final ObjectMapper objectMapper;

    @Override
    public void saveAdminCode(AdminCodeRequest request, String code) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("university", request.getUniversity());
        properties.put("positionList", request.getMemberCodePropertiesList());

        saveMemberCode(code, properties);
    }

    @Override
    public String createChallengerCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6).toUpperCase();
    }

    @Override
    public String createAdminCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            sb.append(secureRandom.nextInt(10)); // 0~9 사이의 숫자 생성
        }

        return sb.toString();
    }

    private void saveMemberCode(String code, Map<String, Object> properties) {
        String key = "code:" + code;

        Map<String, String> stringProperties = properties.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            try {
                                return objectMapper.writeValueAsString(entry.getValue());
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));

        redisTemplate.opsForHash().putAll(key, stringProperties);
        redisTemplate.expire(key, EXPIRATION_TIME, TimeUnit.SECONDS); // TTL 설정

    }

}
