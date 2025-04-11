package umc.product.domain.member.repository.redis.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.RefreshToken;
import umc.product.domain.member.repository.redis.MemberRedisRepository;
import umc.product.global.common.exception.RestApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static umc.product.domain.member.status.MemberErrorStatus.INVALID_UMC_CODE;

@Repository
@AllArgsConstructor
public class MemberRedisRepositoryImpl implements MemberRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    //Todo: 임시로 24시간으로 설정
    private static final long CODE_EXPIRATION_TIME = 60 * 60 * 24;
    private static final String CODE_KEY_PREFIX = "code:";
    private static final String CODE_MEMBER_KEY_PREFIX = "codeMember:";
    private static final long REFRESH_EXPIRATION_TIME = 60*60*24*14;
    private static final String REFRESH_KEY_PREFIX = "refreshToken:";


    @Transactional
    @Override
    public RefreshToken saveRefreshToken(String refreshToken, Long memberId) {
        String key = REFRESH_KEY_PREFIX +memberId;
        // 이미 등록된 리프레쉬 토큰이 있다면 지우고 새로운 값 저장
        deleteRefreshToken(memberId);

        RefreshToken token = RefreshToken.builder()
                .memberId(memberId.toString())
                .refreshToken(refreshToken)
                .build();

        // 새로운 리프레쉬 토큰 저장
        redisTemplate.opsForHash().put(key, "refreshToken", token);
        redisTemplate.expire(key, REFRESH_EXPIRATION_TIME, TimeUnit.SECONDS);

        return token;
    }

    @Override
    public void deleteRefreshToken(Long memberId) {
        String key = REFRESH_KEY_PREFIX +memberId;

        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public boolean existRefreshToken(String refreshToken, Long memberId) {
        String key = REFRESH_KEY_PREFIX + memberId;

        RefreshToken storedToken = (RefreshToken) redisTemplate.opsForHash().get(key, "refreshToken");

        return storedToken != null && storedToken.getRefreshToken().equals(refreshToken);
    }

    @Override
    public List<String> getAppCodeList(List<Member> memberList) {
        //memberId를 list로 변환
        List<String> keyList = memberList.stream()
                .map(member -> {
                            return CODE_MEMBER_KEY_PREFIX + member.getId().toString();
                        }
                )
                .collect(Collectors.toList());
        return stringRedisTemplate.opsForValue().multiGet(keyList);
    }

    @Transactional
    @Override
    public void saveAdminCode(String universityName, String code) {
        String key = CODE_KEY_PREFIX + code;
        stringRedisTemplate.opsForValue().set(key, universityName, CODE_EXPIRATION_TIME, TimeUnit.SECONDS);

    }

    @Override
    public String verifyWebAdminCode(String code) {
        String key = CODE_KEY_PREFIX + code;
        String value = stringRedisTemplate.opsForValue().get(key);
        if(value == null) throw new RestApiException(INVALID_UMC_CODE);
        return value;
    }

    @Override
    public Long verifyAppCode(String code) {
        String key = CODE_KEY_PREFIX + code;
        String value = stringRedisTemplate.opsForValue().get(key);
        if(value == null) throw new RestApiException(INVALID_UMC_CODE);
        return Long.parseLong(value);
    }

    /**
     * Redis pipelined + 병렬 처리로 50개씩 묶어서 병렬로 app code를 저장
     */
    @Override
    public void saveAppCodeList(Map<String, Member> codeMap) {
        int threadPoolSize = Runtime.getRuntime().availableProcessors() * 2;
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        final int batchSize = 50;  // 배치 크기 설정
        List<Future<?>> futures = new ArrayList<>();

        List<Map<String, Member>> batches = new ArrayList<>();
        List<String> keys = new ArrayList<>(codeMap.keySet());
        for (int i = 0; i < keys.size(); i += batchSize) {
            int end = Math.min(i + batchSize, keys.size());
            List<String> batchKeys = keys.subList(i, end);
            Map<String, Member> batchMap = new HashMap<>();
            for (String key : batchKeys) {
                batchMap.put(key, codeMap.get(key));
            }
            batches.add(batchMap);
        }

        for (Map<String, Member> batch : batches) {
            //Future로 50개씩 병렬처리
            futures.add(executor.submit(() -> {
                stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                    StringRedisConnection stringRedisConnection = (StringRedisConnection)connection;
                    batch.forEach((code, member) -> {
                        String codeKey = CODE_KEY_PREFIX + code;
                        String codeValue = String.valueOf(member.getId());
                        stringRedisConnection.setEx(codeKey, CODE_EXPIRATION_TIME, codeValue);

                        //역 index 사용 (사용자 조회시 사용)
                        String memberKey = CODE_MEMBER_KEY_PREFIX + member.getId();
                        String memberValue = String.valueOf(code);
                        stringRedisConnection.setEx(memberKey, CODE_EXPIRATION_TIME, memberValue);

                    });
                    return null;
                });
            }));
        }

        // Future가 모두 끝날때 까지 대기
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
