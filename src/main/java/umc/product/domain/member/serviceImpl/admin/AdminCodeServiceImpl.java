package umc.product.domain.member.serviceImpl.admin;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.service.admin.AdminCodeService;
import umc.product.global.common.exception.RestApiException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static umc.product.domain.member.status.MemberErrorStatus.INVALID_UMC_CODE;


@Service
@AllArgsConstructor
public class AdminCodeServiceImpl implements AdminCodeService {
    private final StringRedisTemplate stringRedisTemplate;
    private static final long EXPIRATION_TIME = 60 * 30;

    @Override
    public void saveWebAdminCode(
            String universityName,
            String code
    ) {
        String key = "code:" + code;
        stringRedisTemplate.opsForValue().set(key, universityName, EXPIRATION_TIME, TimeUnit.SECONDS);
    }

    /**
     * Redis pipelined + 병렬 처리로 50개씩 묶어서 병렬로 app code를 저장
     */
    @Override
    public void saveAppCode(Map<String, Member> codeMap) {
        // 서버에서 가능한 프로세서 * 2
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
                        String key = "code:" + code;
                        String value = String.valueOf(member.getId());
                        stringRedisConnection.setEx(key, EXPIRATION_TIME, value);
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


    @Override
    public Map<String, Member> createAppCode(List<Member> memberList) {
        return memberList.stream()
                .collect(Collectors.toMap(
                        //기본적인 UUID를 6자리로 자르면 중복 가능성이 매우 높음
                        //Base62 인코딩을 통해 중복을 줄임
                        member -> toBase62(UUID.randomUUID()),
                        member -> member
                ));
    }

    @Override
    public Map<String, Member> createIndividualAppCode(Member member) {
        return Map.of(toBase62(UUID.randomUUID()), member);
    }

    @Override
    public String createWebAdminCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            sb.append(secureRandom.nextInt(10)); // 0~9 사이의 숫자 생성
        }

        return sb.toString();
    }

    @Override
    public String verifyWebAdminCode(String code) {
        String key = "code:" + code;
        String value = stringRedisTemplate.opsForValue().get(key);
        if(value == null) throw new RestApiException(INVALID_UMC_CODE);
        return value;
    }

    private String toBase62(UUID uuid) {
        String hexString = uuid.toString().replaceAll("-", "").substring(0, 6); // 16진수 6자리
        long decimalValue = Long.parseLong(hexString, 16);
        return encodeBase62(decimalValue);
    }

    /**
     * Base62를 사용하여 6자리의 임의의 string으로 encode
     */
    private String encodeBase62(long value) {
        final String base62Chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(base62Chars.charAt((int) (value % 62)));
            value /= 62;
        }
        while (sb.length() < 6) {
            sb.append("0"); // 6자리 맞추기
        }
        return sb.reverse().toString();
    }
}
