package umc.product.domain.member.serviceImpl.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.product.domain.member.repository.redis.MemberRedisRepository;
import umc.product.domain.member.service.member.MemberCodeService;


@Service
@RequiredArgsConstructor
public class MemberCodeServiceImpl implements MemberCodeService {
    private final MemberRedisRepository memberRedisRepository;
    @Override
    public Long verifyAppCode(String code) {
        return memberRedisRepository.verifyAppCode(code);
    }
}
