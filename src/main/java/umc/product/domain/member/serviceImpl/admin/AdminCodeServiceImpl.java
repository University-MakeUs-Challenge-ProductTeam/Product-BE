package umc.product.domain.member.serviceImpl.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.repository.redis.MemberRedisRepository;
import umc.product.domain.member.service.admin.AdminCodeService;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@AllArgsConstructor
public class AdminCodeServiceImpl implements AdminCodeService {
    private final MemberRedisRepository memberRedisRepository;

    @Override
    public Map<Long, String> getAppCodeMap(List<Member> memberList) {
        List<String> codeList = memberRedisRepository.getAppCodeList(memberList);

        return IntStream.range(0, memberList.size())
                .boxed()
                .collect(Collectors.toMap(
                        index -> memberList.get(index).getId(),
                        index -> {
                            String code = codeList.get(index);
                            return (code != null) ? code : "";
                        }
                ));
    }

    @Override
    public void saveWebAdminCode(
            String universityName,
            String code
    ) {
        memberRedisRepository.saveAdminCode(universityName,code);
    }

    @Override
    public void saveAppCode(Map<String, Member> codeMap) {
        memberRedisRepository.saveAppCodeList(codeMap);
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
        return memberRedisRepository.verifyWebAdminCode(code);
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
