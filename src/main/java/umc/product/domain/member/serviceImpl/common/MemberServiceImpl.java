package umc.product.domain.member.serviceImpl.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import umc.product.domain.member.dto.request.member.MemberSignUpRequest;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.MemberCode;
import umc.product.domain.member.mapper.MemberMapper;
import umc.product.domain.member.repository.MemberRepository;
import umc.product.domain.member.service.common.MemberService;
import umc.product.domain.member.status.MemberErrorStatus;
import umc.product.global.common.exception.RestApiException;
import umc.product.global.config.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import static umc.product.global.common.exception.code.status.CodeErrorStatus.NOT_VAILD_CODE;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    private final MemberMapper memberMapper;

    @Override
    public Member toCommonMember(MemberSignUpRequest request, String avatarUrl) {
        return memberMapper.toCommonMember(request, avatarUrl);
    }

    public Member findById(Long id) throws UsernameNotFoundException {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RestApiException(MemberErrorStatus.EMPTY_MEMBER));
    }

    // 회원 저장
    public Member saveEntity(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public MemberCode verifyMemberCode(String code) {
        String key = "code:" + code;
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        try {
            // JSON -> Object 변환
            Map<String, Object> properties = entries.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> {
                                try {
                                    return objectMapper.readValue(e.getValue().toString(), Object.class);
                                } catch (JsonProcessingException ex) {
                                    throw new RestApiException(NOT_VAILD_CODE);
                                }
                            }
                    ));

            return MemberCode.builder()
                    .code(code)
                    .properties(properties)
                    .build();

        } catch (Exception e) {
            throw new RestApiException(NOT_VAILD_CODE);
        }
    }

    @Transactional
    @Override
    public Member modifyMyProfileAvatar(Member member, String avatarUrl) {
        member.setAvatarUrl(avatarUrl);
        return memberRepository.save(member);
    }

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RestApiException(MemberErrorStatus.UNAUTHORIZED); // 로그인 안함
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof PrincipalDetails) {
            return ((PrincipalDetails) principal).getMember();
        }

        throw new RestApiException(MemberErrorStatus.AUTHENTICATION_FAILED); // 로그인 정보를 확인할 수 없음

    }
}
